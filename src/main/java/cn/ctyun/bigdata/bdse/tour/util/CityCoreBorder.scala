package cn.ctyun.bigdata.bdse.tour.util


import java.util

import org.apache.spark.{SparkConf, SparkContext}
import cn.ctyun.bigdata.bdse.common.algorithm.grid.{Grid, GridNeighbour}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
/**
  * Created by 98726 on 2017/8/15.
  */
object CityCoreBorder {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf ().setAppName ("AggregateOperator")
    conf.set("spark.master", "local")
    val sc = new SparkContext (conf)
    val rdd = sc.textFile ("spark-common/bjwork2")
    val cacheRDD = rdd.map(x => (x.split("\t")(0),x.split("\t")(1).toInt)).cache()

    //总的网格数
    val count = cacheRDD.count()
    //总的人数
    val number = cacheRDD.reduce((x, y) => ("", x._2 + y._2))._2

    //平均网格人数
    val threshold = number/count
    //过滤掉人数小于平均数的网格
    val cacheRDD2 = cacheRDD.filter(_._2>threshold).cache()
    val number2 = cacheRDD2.reduce((x, y) => ("", x._2 + y._2))._2

    //去掉独立存在的网格
    val cacheRDD3 = cacheRDD2.flatMap(x=>{
      val grids = GridNeighbour.getNeighbours(x._1.toLong, 1)
      grids.map(y=>
        (x._1,y.toString)
      )
    }).cache()

    val map = cacheRDD3.map(x=> (x._2 + "-" + x._1,1)).union(cacheRDD3.map(x=> (x._1 + "-" + x._2,1)))
      .reduceByKey(_+_)
      .filter(_._2==2).map(x=>(x._1.split("-")(0),x._1.split("-")(1))).groupByKey().collectAsMap()

    val hashmap = new util.HashMap[String, util.ArrayList[String]]
    map.foreach(x=>{
      val list = new util.ArrayList[String]
      x._2.foreach(list.add)
      hashmap.put(x._1,list)
    })
    //print(hashmap)
    val hash2 = CityCoreBorderCluster.main(hashmap)
    //print(hash2.get("116470039870040"))


    /*//中心点经纬度
    var centerDot = cacheRDD.map(i=>{
      val center = Grid.getCenter(i._1.toLong)
      (center.x*i._2 , center.y*i._2)
    }).reduce((x,y)=>(x._1 + y._1 , x._2 + y._2))
    centerDot = Tuple2(centerDot._1/number,centerDot._2/number)*/


  }
}
