package io.growing.openapi

/**
 * 链接创建api
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/8/24
 */
object LinksOpenApi extends App {

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


  /**
   * 创建-吸引用户直接打开 App
   */
  //request body
  //使用自己创建活动后得到的活动id和渠道id
  val deep =
  """
    |{
    |        "name": "0523信息流推广",
    |        "productIdIos": "rREJ88PL",
    |        "channelId": "d4PY3M9M",
    |        "campaignIdIos": "GPndkDE9",
    |        "downloadUrlIos": "http://www.growingio.com"
    |}
    |""".stripMargin

  //创建
  //@see https://github.com/lihaoyi/redquests-scala  python requests库的Scala封装版本,用法一样
  val dp = requests.post(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/deeplinks", data = deep, headers = postHeaders)
  val dpPrettyFormatStr = AuthUtils.jsonFormat(dp.text())
  println(dpPrettyFormatStr)

  //查询
  val dg = requests.get(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/deeplinks", headers = getHeaders)
  val dgPrettyFormatStr = AuthUtils.jsonFormat(dg.text())
  println(dgPrettyFormatStr)


  /** *
   * 同时推广 IOS 和 Android 两个平台
   */
  val one =
    """
      |{
      |    "name": "tt3ts2",
      |    "productIdIos": "rREJ88PL",
      |    "productIdAndroid": "LPdgKARN",
      |    "redirectUrl": "http://www.download.com",
      |    "campaignIdIos": "4AovZoza",
      |    "campaignIdAndroid": "G39l3o20",
      |    "channelId": "yYo10lPl"
      |}
      |""".stripMargin
  //创建
  val op = requests.post(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/onelinks", data = one, headers = postHeaders)
  val opPrettyFormatStr = AuthUtils.jsonFormat(op.text())
  println(opPrettyFormatStr)

  //查询
  val og = requests.get(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/onelinks", headers = getHeaders)
  val ogPrettyFormatStr = AuthUtils.jsonFormat(og.text())
  println(ogPrettyFormatStr)

  /** *
   * 推广 IOS 或 Android 单个平台
   */
  val normal =
    """
      | {
      |        "name": "normallinkstest3",
      |        "productId": "LPdgKARN",
      |        "channelId": "yYo10lPl",
      |        "campaignId": "G39l3o20",
      |        "redirectUrl": "http://www.growingio.com"
      | }
      |""".stripMargin
  //创建
  val np = requests.post(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/normallinks", data = normal, headers = postHeaders)
  val npPrettyFormatStr = AuthUtils.jsonFormat(np.text())
  println(npPrettyFormatStr)

  //查询
  val ng = requests.get(s"https://www.growingio.com/api/v1/projects/$projectUid/meta/normallinks", headers = getHeaders)
  val ngPrettyFormatStr = AuthUtils.jsonFormat(ng.text())
  println(ngPrettyFormatStr)

}
