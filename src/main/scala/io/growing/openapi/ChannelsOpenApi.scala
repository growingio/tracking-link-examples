package io.growing.openapi

/**
 * 渠道api
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/8/24
 */
object ChannelsOpenApi extends App {

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
  val data =
    """
      |{
      |  "name":"二维码推广"
      |}
      |""".stripMargin

  //创建渠道
  //@see https://github.com/lihaoyi/redquests-scala  python requests库的Scala封装版本,用法一样
  val channelsPost = requests.post(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/channels", data = data, headers = postHeaders)
  val channelsPostPrettyFormatStr = AuthUtils.jsonFormat(channelsPost.text())
  println(channelsPostPrettyFormatStr)

  //查询渠道
  val channelsGet = requests.get(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/channels", headers = getHeaders)
  val channelsGetPrettyFormatStr = AuthUtils.jsonFormat(channelsGet.text())
  println(channelsGetPrettyFormatStr)
}
