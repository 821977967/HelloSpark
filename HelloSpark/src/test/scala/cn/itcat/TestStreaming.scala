package cn.itcat

import org.apache.spark.{SparkConf, SparkContext}

object TestStreaming {
  def main(args: Array[String]): Unit = {
    val  sc = new SparkContext(new SparkConf().setAppName("LiuShuai Streaming test"))
  }
}
