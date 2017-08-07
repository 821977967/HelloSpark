package cn.itcast.spark.day5

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._


object LSJsonTest {
  def main(args: Array[String]) {
    val json = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}"
    json2Map(json)
  }

  def json2Map(json: String) {
    implicit val formats = DefaultFormats
    //解析结果
    val responseInfo: ResponseInfo = parse(json).extract[ResponseInfo]
    println(responseInfo)
    //数组数据
    val addressArray = responseInfo.address
    val linkArray = responseInfo.links
    println(addressArray)
    for (link <- linkArray) {
      println("linkArray：" + link)
    }

  }

  //二级列表
  case class AddressInfo(street: String, city: String, country: String) {
    override def toString = s"street:$street,  city:$city,  county:$country"
  }

  case class LinkInfo(name: String, url: String) {
    override def toString = s"name:$name,  url:$url"
  }

  //一级列表
  case class ResponseInfo(name: String, url: String, page: Integer,
                          isNonProfit: Boolean, address: AddressInfo,
                          links: Array[LinkInfo]) {
    override def toString = s"name:$name,  url:$url, page:$page,  isNonProfit:$isNonProfit,  address:$address,  links:$links"
  }



}
