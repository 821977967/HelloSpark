package cn.itcast.spark.day5

import com.alibaba.fastjson.JSON
import net.sf.json.JSONObject
import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.api.java.function.VoidFunction
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.json4s.jackson.JsonMethods._

object LSKafkaWordCount {

  //  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
  //    //iter.flatMap(it=>Some(it._2.sum + it._3.getOrElse(0)).map(x=>(it._1,x)))
  //    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
  //  }
  case class PlaneInfo(offset: String)

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels()
    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("LSKafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("/Users/liushuai/Desktop/temp/kafkalogs")

    //"alog-2016-04-16,alog-2016-04-17,alog-2016-04-18"
    //"Array((alog-2016-04-16, 2), (alog-2016-04-17, 2), (alog-2016-04-18, 2))"
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val dstream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER)
    val lines = dstream.flatMap(line => {
      val data = JSON.parseObject(line._2)
      Some(data)
    })

    lines.print()
/**/
//    val method = lines.map(x => (x.getString("method"), 1))
//    val ll = method.reduceByKey(_ + _)
//    ll.print()


    //    对json数据格式进行解析demo
    /*  val beat = lines.map(x => x.getJSONObject("beat"))
        val hostname = beat.map(x => x.getString("hostname"))
        val version = beat.map(x => x.getString("version"))
        val timestamp = lines.map(x => x.getString("timestamp"))
        hostname.print()
        version.print()
        timestamp.print()*/

    //    val line = lines.map(x => (x.type.getString("method"), x.getInt("offset")))
    //    val fields = messages.map(_.split("\t"))
    //    fields.print()
    //val userClicks = events.map(x => (x.getString("uid"), x.getInt("click_count"))).reduceByKey(_ + _)

    //    val jsonStr = messages.toString
    //    val json = JSON.parseObject(jsonStr)
    //    val offset = json.getString("offset")
    //    val beat = json.getJSONObject("beat")
    //    val hostname = beat.getString("hostname")
    //    val version = beat.getString("version")
    //    println("offset:" + offset )
    ssc.start()
    ssc.awaitTermination()

    //    println("ms1:" + ms1 + "," + "ms2:" + ms2)
    //    val words = data.map(_._2).flatMap(_.split(" "))
    //    val wordCounts = words.map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)

  }
}

/*  对json数据格式进行解析demo
    val beat = lines.map(x => x.getJSONObject("beat"))
    val hostname = beat.map(x => x.getString("hostname"))
    val version = beat.map(x => x.getString("version"))
    val timestamp = lines.map(x => x.getString("timestamp"))
    hostname.print()
    version.print()
    timestamp.print()


    val jsonStr = "{\"IdType\":\"IP\",\"Timestamp\":1475222328000,\"datatype\":\"coordinates\",\"location\":{\"x\":313.0,\"y\":205.0,\"z\":23},\"userid\":[\"0ac94949\"],\"map\":{\"mapid\":\"23\"}}"
    val json = JSON.parseObject(jsonStr)

    val idType = json.getString("IdType")
    val timestamp = json.getLong("Timestamp")
    val dataType = json.getString("datatype")

    val location = json.getJSONObject("location")
    val x = location.getInteger("x")
    val y = location.getInteger("y")
    val z = location.getInteger("z")

    val userid = json.getJSONArray("userid")
    for( i <- 0.to(userid.size) ) {
      println(userid.getString(i))
    }

    val map = json.getJSONObject("map")
    val mapid = map.getString("mapid")
  }
    */
