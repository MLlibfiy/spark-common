package cn.ctyun.bigdata.bdse.tour.util


import org.apache.spark.{SparkConf, SparkContext}
import cn.ctyun.bigdata.bdse.common.algorithm.grid.Grid
/**
  * Created by 98726 on 2017/8/15.
  */
object CityCoreBorder {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf ().setAppName ("AggregateOperator")
    conf.set("spark.master", "local")
    val sc = new SparkContext (conf)
    val rdd = sc.textFile ("spark-common/bjwork")
    val cacheRDD = rdd.map(x => (x.split("\t")(0),x.split("\t")(1).toInt*x.split("\t")(1).toInt)).cache()
    val count = cacheRDD.count()
    val number = cacheRDD.reduce((x, y) => ("", x._2 + y._2))._2
    //val threshold = number/count
    //val cacheRDD2 = cacheRDD.filter(_._2>threshold).cache()
   // val number2 = cacheRDD2.reduce((x, y) => ("", x._2 + y._2))._2

    val centerDot = cacheRDD.map(i=>{
      val center = Grid.getCenter(i._1.toLong)
      (center.x*i._2 , center.y*i._2)
    }).reduce((x,y)=>(x._1 + y._1 , x._2 + y._2))
    print(centerDot._1/number,centerDot._2/number)
  }
}
