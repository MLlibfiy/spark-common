package cn.ctyun.bigdata.bdse.tour.util

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by 98726 on 2017/8/16.
  */
object PoiPoint {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf ().setAppName ("AggregateOperator")
    conf.set("spark.master", "local")
    val sc = new SparkContext (conf)
    val rdd = sc.textFile ("D:\\work\\电信云计算公司\\四维数据\\mif_poi_province\\beijing.mid")
    print(rdd.map(x=>x.split(",")(3)+" "+x.split(",")(4)).reduce(_+","+_))
    //sc.parallelize(rdd.map(x=>x.split(",")(3)+" "+x.split(",")(4)).reduce(_+","+_)).saveAsTextFile("spark-common/beijingpoi")


  }
}
