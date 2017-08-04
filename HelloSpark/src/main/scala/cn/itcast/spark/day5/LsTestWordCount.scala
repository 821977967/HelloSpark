package cn.itcast.spark.day5

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object LsTestWordCount {
  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    iter.map { case (word, current_num, his_num) => (word, current_num.sum + his_num.getOrElse(0)) }
  }

  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("Liushuai streaming test"))
    sc.setCheckpointDir("/Users/liushuai/Desktop/temp")
    val scc = new StreamingContext(sc, Seconds(3))
    val ds = scc.socketTextStream("10.199.73.149", 8888)

    val result = ds.flatMap(_.split(" ")).map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(sc.defaultParallelism), true)
    result.print()
    scc.start()
    scc.awaitTermination()
  }
}
