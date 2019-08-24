package io.growing.openapi

/**
 * 产品api
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/8/24
 */
object AppOpenApi extends App {

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


  //查询产品
  //@see https://github.com/lihaoyi/redquests-scala  python requests库的Scala封装版本,用法一样
  val productsGet = requests.get(s"https://www.growingio.com/api/v1/projects/${projectUid}/meta/products", headers = getHeaders)
  //格式化json
  val productsGetPrettyFormatStr = AuthUtils.jsonFormat(productsGet.text())
  println(productsGetPrettyFormatStr)

}
