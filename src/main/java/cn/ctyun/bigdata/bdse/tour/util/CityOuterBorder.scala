package cn.ctyun.bigdata.bdse.tour.util

import cn.ctyun.bigdata.bdse.common.global.SSXRelation
import cn.ctyun.bigdata.bdse.common.math.Polygon
import cn.ctyun.bigdata.bdse.common.util.DataFormatUtil
//import cn.ctyun.bigdata.bdse.tour.index.around.tourist.AroundLevelEnum
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by 98726 on 2017/8/21.
  */
object CityOuterBorder {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf ().setAppName ("AggregateOperator")
    conf.set("spark.master", "local")
    val sc = new SparkContext (conf)
    val rdd = sc.textFile ("spark-common/citycoreborder")
    /*rdd.map(x=> {
      val border = x.split ("\t");
      //城市级别
      val cityLevel = SSXRelation.CITY_LEVEL.get (border (0))
      //获取城市边界扩大的范围 单位公里
      /* val length = AroundLevelEnum.getlength (cityLevel)
      //城市边界
      val corePolygon = new Polygon (border(2))
      //获取城市扩大之后的边界
      val polygon1 = corePolygon.enlargeN (length * 1000)
      val cityOuterBorder = DataFormatUtil.listBoundary2String (polygon1.getBoundary)
      border(0)+"\t"+border(1)+"\t"+cityOuterBorder
    }).saveAsTextFile("spark-common/cityouterborder")*/
    }*/
  }
}
