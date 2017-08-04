package cn.itcast.spark.day5

import kafka.serializer.StringDecoder
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka.KafkaUtils
import net.sf.json.JSONObject

object LsTestKafka {
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("LsTestKafka"))
    val ssc = new StreamingContext(sc, Seconds(5))
    // Kafka configurations
    val topics = "message"
    val brokers = "10.199.109.222:9092,10.199.109.223:9092,10.199.109.224:9092"


    // Create a direct stream
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers,"serializer.class" -> "kafka.serializer.StringEncoder")
    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    val events = kafkaStream.flatMap(line => {
      val data = JSONObject.fromObject(line._2)
      Some(data)
    })


    // Compute user click times
    //      val userClicks = events.map(x => (x.getString("uid"), x.getInt("click_count"))).reduceByKey(_ + _)
    //      userClicks.foreachRDD(rdd => {
    //        rdd.foreachPartition(partitionOfRecords => {
    //          partitionOfRecords.foreach(pair => {
    //            val uid = pair._1
    //            val clickCount = pair._2
    //            val jedis = RedisClient.pool.getResource
    //            jedis.select(dbIndex)
    //            jedis.hincrBy(clickHashKey, uid, clickCount)
    //            RedisClient.pool.returnResource(jedis)
    //          })
    //        })
    //      })

    ssc.start()
    ssc.awaitTermination()
  }
}
