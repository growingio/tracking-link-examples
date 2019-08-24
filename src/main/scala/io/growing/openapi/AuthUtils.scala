package io.growing.openapi

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Hex

/**
 * 授权工具api
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/8/24
 */
object AuthUtils {

  /**
   * 配置自己的公司的
   */
  val projectUid = "4PYJMWoM" //项目UID
  val secret = "" //项目私钥
  val `X-Client-Id` = "" //项目公钥
  val ai = "" //项目id
  val time = System.currentTimeMillis() //当前授权的时间

  /**
   *
   * @param secret
   * @param project
   * @param ai
   * @param tm
   * @return status,code
   */
  def authToken(secret: String, project: String, ai: String, tm: Long) = {
    val messages = s"POST\n/auth/token\nproject=$project&ai=$ai&tm=$tm"
    val hmac = Mac.getInstance("HmacSHA256")
    hmac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"))
    val signature = hmac.doFinal(messages.getBytes("UTF-8"))
    Hex.encodeHexString(signature)
  }

  def getToken(ai: String, projectUid: String, time: Long, auth: String, `X-Client-Id`: String) = {
    //根据计算得到的quth进行验证，获取tokne
    val res = requests.post(s"https://www.growingio.com/auth/token?ai=${ai}&project=${projectUid}&tm=${time}&auth=${auth}",
      headers = Map("X-Client-Id" -> `X-Client-Id`))
    val obj = JSON.parseObject(res.text())
    val token = if (obj.containsKey("status")) {
      obj.getString("code")
    } else {
      throw new Exception("error")
    }
    token
  }

  /**
   * 格式化
   *
   * @param jsonString
   */
  def jsonFormat(jsonString: String): String = {
    val obj = JSON.parse(jsonString)
    JSON.toJSONString(obj, SerializerFeature.PrettyFormat,
      SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat)
  }
}