package cn.ctyun.bigdata.forecast

import org.apache.spark.mllib.linalg.SparseVector
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by 98726 on 2017/8/28.
  */
object TrainingModel {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("RCMD").setMaster("local"))
    val data: RDD[Array[String]] = sc.textFile("spark-common/游客预测.txt").map(_.split("\t"))
    //    拿到所有特征值去重得到所有样本的所有特征，zip一个下标，构建为一个大向量
    val dist: collection.Map[String, Long] = data.flatMap(_(1).split(";")).map(_.split(":")(0)).distinct().zipWithIndex().collectAsMap()
    //    构建labeledPoint的RDD
    val train: RDD[LabeledPoint] = data.map(sample=>{
      //  拿到所有非零元素下标，拿每个特征取在上面大向量中映射到的下标位置
      val indexs = sample(1).split(";").map(_.split(":")(0)).map(feature=>{
      val index: Long = dist.getOrElse(feature,-1L)
      index.toInt
    })
    //  构建稀疏向量  构建labeledPoint完成
    new LabeledPoint(sample(0).toLong, new SparseVector(dist.size,indexs,Array.fill(indexs.length)(1.0)))
    })
    var splits = train.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))
    //模型训练
    val lr = new LinearRegressionWithSGD()
    lr.setIntercept(true)
    val model=lr.run(train)
    train.foreach{point=>println(point.label+"======="+model.predict(point.features)) }
    dist.map(x=>(x._2,x._1)).toList.sortBy(_._1).foreach(println(_))
    //println("正确率="+(1.0-result.mean()))
    println(model.weights.toArray.mkString("\t"))
    println(model.intercept)

    //model.save(sc,"spark-common/model")

  }
}
