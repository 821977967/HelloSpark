package cn.itcast.spark.day5

import com.alibaba.fastjson.JSON

object LSKafkaJsonTest {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,6)
//   val fil = arr.filter(x => (x._1 == 1))
//    println(fil)
//    val a = arr.get
  /*  val jsonStr = "{\"header\":{\"eventLength\":362,\"eventType\":\"UPDATE\",\"executeTime\":1501892896000,\"logfileName\":\"mysql-bin.000142\",\"logfileOffset\":605976764,\"schemaName\":\"jira_it\",\"serverId\":51113306,\"serverenCode\":\"UTF-8\",\"sourceType\":\"MYSQL\",\"tableName\":\"clusteredjob\",\"version\":1},\"rowList\":[{\"afterColumns\":[{\"index\":0,\"key\":true,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"ID\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"10412\"},{\"index\":1,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"JOB_ID\",\"null\":false,\"sqlType\":12,\"updated\":false,\"value\":\"com.atlassian.jira.plugins.dvcs.scheduler.DvcsScheduler:job\"},{\"index\":2,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"JOB_RUNNER_KEY\",\"null\":false,\"sqlType\":12,\"updated\":false,\"value\":\"com.atlassian.jira.plugins.dvcs.scheduler.DvcsScheduler\"},{\"index\":3,\"key\":false,\"length\":0,\"myqlType\":\"char(1)\",\"name\":\"SCHED_TYPE\",\"null\":false,\"sqlType\":1,\"updated\":false,\"value\":\"I\"},{\"index\":4,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"INTERVAL_MILLIS\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"3600000\"},{\"index\":5,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"FIRST_RUN\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"1494386809334\"},{\"index\":6,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"CRON_EXPRESSION\",\"null\":true,\"sqlType\":12,\"updated\":false,\"value\":\"\"},{\"index\":7,\"key\":false,\"length\":0,\"myqlType\":\"varchar(60)\",\"name\":\"TIME_ZONE\",\"null\":true,\"sqlType\":12,\"updated\":false,\"value\":\"\"},{\"index\":8,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"NEXT_RUN\",\"null\":false,\"sqlType\":3,\"updated\":true,\"value\":\"1501896438683\"},{\"index\":9,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"VERSION\",\"null\":false,\"sqlType\":3,\"updated\":true,\"value\":\"2087\"},{\"index\":10,\"key\":false,\"length\":0,\"myqlType\":\"blob\",\"name\":\"PARAMETERS\",\"null\":true,\"sqlType\":-4,\"updated\":false,\"value\":\"\"}],\"beforeColumns\":[{\"index\":0,\"key\":true,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"ID\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"10412\"},{\"index\":1,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"JOB_ID\",\"null\":false,\"sqlType\":12,\"updated\":false,\"value\":\"com.atlassian.jira.plugins.dvcs.scheduler.DvcsScheduler:job\"},{\"index\":2,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"JOB_RUNNER_KEY\",\"null\":false,\"sqlType\":12,\"updated\":false,\"value\":\"com.atlassian.jira.plugins.dvcs.scheduler.DvcsScheduler\"},{\"index\":3,\"key\":false,\"length\":0,\"myqlType\":\"char(1)\",\"name\":\"SCHED_TYPE\",\"null\":false,\"sqlType\":1,\"updated\":false,\"value\":\"I\"},{\"index\":4,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"INTERVAL_MILLIS\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"3600000\"},{\"index\":5,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"FIRST_RUN\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"1494386809334\"},{\"index\":6,\"key\":false,\"length\":0,\"myqlType\":\"varchar(255)\",\"name\":\"CRON_EXPRESSION\",\"null\":true,\"sqlType\":12,\"updated\":false,\"value\":\"\"},{\"index\":7,\"key\":false,\"length\":0,\"myqlType\":\"varchar(60)\",\"name\":\"TIME_ZONE\",\"null\":true,\"sqlType\":12,\"updated\":false,\"value\":\"\"},{\"index\":8,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"NEXT_RUN\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"1501892838683\"},{\"index\":9,\"key\":false,\"length\":0,\"myqlType\":\"decimal(18,0)\",\"name\":\"VERSION\",\"null\":false,\"sqlType\":3,\"updated\":false,\"value\":\"2086\"},{\"index\":10,\"key\":false,\"length\":0,\"myqlType\":\"blob\",\"name\":\"PARAMETERS\",\"null\":true,\"sqlType\":-4,\"updated\":false,\"value\":\"\"}]}]}"
    val json = JSON.parseObject(jsonStr)
*/
    //    val header = json.getJSONObject("header")
    //    val eventType = header.getString("eventType")
    //    println("eventType:" + eventType)
/*
    val rowList = json.getJSONArray("rowList")
    val obj = rowList.getJSONObject(0)
    val afterColumns = obj.getJSONArray("afterColumns")
    val after0 = afterColumns.getJSONObject(0)
    val after0_name = after0.getString("name")
    val after0_value = after0.getString("value")
    println(after0_name + ":" + after0_value)*/
    //    for (i <- 0 to (afterColumns.size())) {
    //      println(afterColumns.getString(i))
    //    }
    //    val idType = json.getString("IdType")
    //    val timestamp = json.getLong("Timestamp")
    //
    //    val location = json.getJSONObject("location")
    //    val x = location.getInteger("x")
    //    val y = location.getInteger("y")
    //
    //    val userid = json.getJSONArray("userid")
    //    for (i <- 0.to(userid.size)) {
    //      println(userid.getString(i))
    //    }
    //
    //    val map = json.getJSONObject("map")
    //    val mapid = map.getString("mapid")
  }

}
