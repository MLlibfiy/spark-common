package cn.ctyun.bigdata.bdse.tour.util

import cn.ctyun.bigdata.bdse.common.algorithm.Geography
import cn.ctyun.bigdata.bdse.common.algorithm.grid.{Grid, GridNeighbour}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by 98726 on 2017/8/1.
  */
object CountyGridsBorder {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf ().setAppName ("AggregateOperator")
    conf.set("spark.master", "local")
    val sc = new SparkContext (conf)
    val rdd = sc.textFile ("spark-common/bjgrid")
    val gridsRDD = rdd.filter (x => { x.split ("\t")(9).equals ("1")})
      .map (x => (x.split ("\t")(4) + "\t" + x.split ("\t")(7), x.split ("\t")(0)))
      .groupByKey()
    val borderGridsRDD = gridsRDD.map(x => {
      (x._1, x._2.filter (i => {
        var flag = 0;
        val grids = GridNeighbour.getNeighbours (i.toLong, 1)
        grids.foreach (k => {
          x._2.foreach (j => {
            if (k.toString.equals (j.trim)) flag += 1
          })
        })
        flag < 7
      }))
    })
    borderGridsRDD.map(x=>{
      val list = x._2.toBuffer
      val result = ArrayBuffer[String]()
      result.+=(list.head)
      list.remove(0)
      while (list.nonEmpty){
        var maxlength = Double.MaxValue
        var next = result(result.length-1)
        var raw = Grid.getCenter(next.toLong)
        var index = 0
        for (i <- list.indices) {
          val compare = Grid.getCenter(list(i).toLong)
          val length = Geography.calculateLength(raw.x,raw.y,compare.x,compare.y)
          if(length<maxlength){
            next = list(i)
            index = i
            maxlength = length
          }
        }
        list.remove(index)
        if (maxlength < 6000) {
          result.+=(next)
        }
      }
      (x._1,result)
    }).map(x => {
      var result = new StringBuffer()
      result.append(x._1+"\t")
      x._2.foreach(i=>{
        val grid = Grid.getCenter(i.toLong)
        result.append(grid.x.formatted("%.4f")+" "+grid.y.formatted("%.4f")+",")
      })
      result.deleteCharAt(result.length()-1)
    }).foreach(f = print)

     // .saveAsTextFile("/proripc/bdse/develop/cityborder")
  }
}
