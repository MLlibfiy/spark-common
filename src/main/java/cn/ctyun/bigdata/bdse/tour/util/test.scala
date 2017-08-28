package cn.ctyun.bigdata.bdse.tour.util

import org.apache.spark
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, StreamingLinearRegressionWithSGD}
import org.apache.spark.streaming.{Seconds, StreamingContext}
/**
  * Created by 98726 on 2017/8/1.
  */
object test {
  def main(args: Array[String]) {
/*
    val conf = new SparkConf().setAppName("MapOperator").setMaster("local")
    val sc = new SparkContext(conf)


    val array = Array("hello,xuruyun","hello,liangyongqi")

    val nameRdd = sc.parallelize(array)

    val mapRDD = nameRdd.map { _.split(",") }
    val mapResults = mapRDD.collect()
    for( result <- mapResults)
      println(result(0))

    /*    val flatMapRDD = nameRdd.flatMap{ _.split(",") }
        val flatMapResults = flatMapRDD.collect()
        for( result <- flatMapResults)
          println(result)*/
    //    val results = nameRdd.map( x => {
    //        println("map:"+x)
    //         "Hello\t"+x
    //    }).filter { x => println("filter:" +x );  true}.count
    //
*/

    /*val result = ArrayBuffer[String]()
    result.+=("a").+=("b").+=("b").+=("a")
    println(result(result.length-1))

    var str = new StringBuffer("asdfghsssssssssssssssssbb")
    print(str.deleteCharAt(str.length()-1))*/




    // Load training data
    //val conf = new SparkConf().setAppName("MapOperator").setMaster("local")
    //val sc = new SparkContext(conf)
    val ssc = new StreamingContext("local[2]","NetworkWordCount",  Seconds(1))
    val trainingData = ssc.textFileStream("/training/data/dir").map(LabeledPoint.parse).cache()
    val testData = ssc.textFileStream("/testing/data/dir").map(LabeledPoint.parse)
    val numFeatures = 3
    val model = new StreamingLinearRegressionWithSGD()
      .setInitialWeights(Vectors.zeros(numFeatures))
    model.trainOn(trainingData)
    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()

    ssc.start()
    ssc.awaitTermination()

  }
}
