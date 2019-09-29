package io.growing.openapi

/**
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/9/26
 */
object WebLinksOpenApi extends App {

  import io.growing.openapi.AuthUtils._

  /**
   * POST请求的headers
   */
  def postHeaders = {
    val auth = AuthUtils.authToken(secret, projectUid, ai, time) //获取验证auth
    val token = AuthUtils.getToken(ai, projectUid, time, auth, `X-Client-Id`) //获取token
    Map("Authorization" -> token, "X-Client-Id" -> `X-Client-Id`, "Content-Type" -> "application/json")
  }

  /**
   * GET请求的headers
   */
  def getHeaders = {
    val auth = AuthUtils.authToken(secret, projectUid, ai, time) //获取验证auth
    val token = AuthUtils.getToken(ai, projectUid, time, auth, `X-Client-Id`) //获取token
    Map("Authorization" -> token, "X-Client-Id" -> `X-Client-Id`)
  }

  /**
   * web 监测链接创建 App
   */
  //request body
  //使用自己创建活动后得到的活动id和渠道id
  val web =
  """
    |{
    |    "redirectUrl":"http://www.www.www",
    |    "utmMedium":"对对对",
    |    "utmTerm":"免费观看",
    |    "utmContent":"内容",
    |    "comments":"备注",
    |    "name":"测试web8",
    |    "campaignId":"xogjZQ2P",
    |    "channelId":"vnomv9zJ"
    |}
    |""".stripMargin

  //创建
  //@see https://github.com/lihaoyi/redquests-scala  python requests库的Scala封装版本,用法一样
  val weblink = requests.post(s"https://www.growingio.com/api/v2/projects/$projectUid/activities", data = web, headers = postHeaders)
  val weblinkPrettyFormatStr = AuthUtils.jsonFormat(weblink.text())
  println(weblinkPrettyFormatStr)

  //查询
  val wb = requests.get(s"https://www.growingio.com/api/v2/projects/$projectUid/activities", headers = getHeaders)
  val wbPrettyFormatStr = AuthUtils.jsonFormat(wb.text())
  println(wbPrettyFormatStr)
}
