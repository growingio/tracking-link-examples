package io.growing.openapi

/**
 * 活动api
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/8/24
 */
object CampaignsOpenApi extends App {

  import io.growing.openapi.AuthUtils._

  /**
   * GET请求的headers
   */
  def getHeaders = {
    val auth = AuthUtils.authToken(secret, projectUid, ai, time) //获取验证auth
    val token = AuthUtils.getToken(ai, projectUid, time, auth, `X-Client-Id`) //获取token
    Map("Authorization" -> token, "X-Client-Id" -> `X-Client-Id`)
  }

  /**
   * POST请求的headers
   */
  def postHeaders = {
    val auth = AuthUtils.authToken(secret, projectUid, ai, time) //获取验证auth
    val token = AuthUtils.getToken(ai, projectUid, time, auth, `X-Client-Id`) //获取token
    Map("Authorization" -> token, "X-Client-Id" -> `X-Client-Id`, "Content-Type" -> "application/json")
  }

  //request body
  val date =
    """{
      |  "name":"双十一推广_2"
      |}""".stripMargin

  //创建活动
  //@see https://github.com/lihaoyi/redquests-scala  python requests库的Scala封装版本,用法一样
  val campaignsPost = requests.post(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/campaigns", data = date, headers = postHeaders)
  val campaignsPostPrettyFormatStr = AuthUtils.jsonFormat(campaignsPost.text())
  println(campaignsPostPrettyFormatStr)

  //查询活动
  val campaignsGet = requests.get(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/campaigns", headers = getHeaders)
  val campaignsGetPrettyFormatStr = AuthUtils.jsonFormat(campaignsGet.text())
  println(campaignsGetPrettyFormatStr)
}
