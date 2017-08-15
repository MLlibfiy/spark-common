package cn.ctyun.bigdata.bdse.tour.util

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

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

    val result = ArrayBuffer[String]()
    result.+=("a").+=("b").+=("b").+=("a")
    println(result(result.length-1))

    var str = new StringBuffer("asdfghsssssssssssssssssbb")
    print(str.deleteCharAt(str.length()-1))


  }
}
