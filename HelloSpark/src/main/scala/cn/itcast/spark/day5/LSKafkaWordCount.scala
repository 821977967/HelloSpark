package cn.itcast.spark.day5

import net.sf.json.JSONObject
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
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("/Users/liushuai/Desktop/temp/kafkalogs")



    //"alog-2016-04-16,alog-2016-04-17,alog-2016-04-18"
    //"Array((alog-2016-04-16, 2), (alog-2016-04-17, 2), (alog-2016-04-18, 2))"
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val data = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER)

    //    data.print()
    //    val messages = data.flatMap(line => {
    //      val data = JSONObject.fromObject(line._2)
    //      Some(data)
    //    })
    //    val msg = messages.map(x => (x.getString("beat"), x.getString("timestamp")))
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

/*
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
*/
