package cn.itcast.spark.day5

import cn.itcast.spark.day5.KafkaWordCount.updateFunc
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

  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    //iter.flatMap(it=>Some(it._2.sum + it._3.getOrElse(0)).map(x=>(it._1,x)))
    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
  }

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels()
    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("LSKafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("/Users/liushuai/Desktop/temp/kafkalogs")

    //"alog-2016-04-16,alog-2016-04-17,alog-2016-04-18"
    //"Array((alog-2016-04-16, 2), (alog-2016-04-17, 2), (alog-2016-04-18, 2))"
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val dstream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER)
    val lines = dstream.flatMap(line => {
      val data = JSON.parseObject(line._2)
      Some(data)
    })
    //    过滤数据取只是inset的json数据
    val lineIn = lines.filter(x => x.getJSONObject("header").getString("eventType").equals("INSERT"))
    //    过滤数据 取只是Y的json数据
    val rowList = lineIn.map(x => x.getJSONArray("rowList"))
    val yRowList = rowList.filter(x => x.getJSONObject(0).getJSONArray("afterColumns").getJSONObject(2).getString("value").equals("Y"))
    val obj = yRowList.map(x => x.getJSONObject(0))
    val after = obj.map(x => x.getJSONArray("afterColumns"))
    val after1 = after.map(x => x.getJSONObject(1))
    val valAfter1 = after1.map(x => ("click_Counts", x.getString("value").toInt))
    //    计算点击总数
    val click_Counts = valAfter1.updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)

    //插入mysql数据库
    click_Counts.foreachRDD(rdd => {
      rdd.foreachPartition(partitionOfRecords =>{
        val conn = ConnectPool.getConnection
        conn.setAutoCommit(false);  //设为手动提交
        val  stmt = conn.createStatement();

        partitionOfRecords.foreach( record => {

          stmt.addBatch("insert into dm_sstreaming_getdata_test (insert_time,click_sum) values (now(),'"+record._2+"')");
        })

        stmt.executeBatch();
        conn.commit();  //提交事务

      })
    })


//    click_Counts.foreachRDD(wd => wd.foreachPartition(
//      data => {
//        val conn = ConnectPool.getConn("root", "1714004716", "hh15", "dg")
//        //val conn = ConnectPool.getConn("root", "1714004716", "h15", "dg")
//        //插入数据
//        //conn.prepareStatement("insert into t_word2(word,num) values('tom',23)").executeUpdate()
//        try {
//          for (row <- data) {
//            println("input data is " + row._1 + "  " + row._2)
//            val sql = "insert into t_word2(word,num) values(" + "'" + row._1 + "'," + row._2 + ")"
//            conn.prepareStatement(sql).executeUpdate()
//          }
//        } finally {
//          conn.close()
//        }
//      }))

//---------------------------------

    click_Counts.print()

    //valAfter1.print()

    //    val data = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER)
    //    val words = data.map(_._2).flatMap(_.split(" "))
    //    val wordCounts = words.map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)


    //    val obj = rowList.map(x => x.getJSONObject(0))
    //    val afterColumns = obj.map(_.getJSONArray("afterColumns"))
    //    val after2 = afterColumns.map(_.getJSONObject(2))
    //    val name_af2 = after2.map(_.getString("value"))

    //    取json里面表内容字段
    //    val rowList = lines.map(x => x.getJSONArray("rowList"))
    //    val obj = rowList.map(x => x.getJSONObject(0))
    //    obj.print()
    //
    //    val afterColumns = obj.map(x => x.getJSONArray("afterColumns"))
    ////    val obj_after = afterColumns.map(x => x.getJSONObject(0))
    //    val obj_after_name = obj_after.map(x => x.getString("name"))
    //    obj_after_name.print()

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
