// common
case class HttpResponse(status: Int ,body: String)

trait HttpTransport {
  def doGet(url: String): HttpResponse
  def doPost(url: String, payload: String): HttpResponse
}

// play-http-verbs
class PlayHttpTransport() extends HttpTransport {
  override def doGet(url: String): HttpResponse = ???

  override def doPost(url: String, payload: String): HttpResponse = ???
}



// http verbs
class HttpClient(val httpTransport: HttpTransport) {
  def get (url:String): HttpResponse = {
    // do your business logic......
    // then
    httpTransport.doGet(url)
  }

  def post(url:String, payload: String): HttpResponse = {
    // do your business logic......
    // then
    httpTransport.doPost(url, payload)
  }


}



// ms
val client = new HttpClient(new PlayHttpTransport)
client.get("google.com")