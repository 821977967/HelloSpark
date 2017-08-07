package cn.itcast.spark.day5

import net.sf.json.JSONObject
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils


object LSKafkaWordCount {

  //  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
  //    //iter.flatMap(it=>Some(it._2.sum + it._3.getOrElse(0)).map(x=>(it._1,x)))
  //    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
  //  }

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
    val messages = data.flatMap(line => {
      val data = JSONObject.fromObject(line._2)
      Some(data)
    })
    val msg = messages.map(x => (x.getString("beat"), x.getString("timestamp")))
    val ms1 = msg.map(y => (y.formatted("hostname"),0))
    ms1.print()
//    println("ms1:" + ms1 + "," + "ms2:" + ms2)
    //    val words = data.map(_._2).flatMap(_.split(" "))
    //    val wordCounts = words.map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)
    ssc.start()
    ssc.awaitTermination()
  }
}

