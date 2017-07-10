/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.http.ws

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}
import org.scalatest.mock.MockitoSugar
import play.api.libs.json.{Json, Writes}
import uk.gov.hmrc.http._

import scala.concurrent.{ExecutionContext, Future}

class WSExtensionsSpec extends FunSpec with MockitoSugar with Matchers with ScalaFutures {

  import uk.gov.hmrc.play.http.ws.WSExtensions._

  case class TestClass(foo: String, bar: Int)
  implicit val tcreads = Json.format[TestClass]
  implicit val hc = HeaderCarrier()


  case class TestRequestClass(baz: String, bar: Int)
  implicit val trcreads = Json.format[TestRequestClass]


  val testClass1 = TestClass("FOO", 100)
  val testClass2 = TestClass("FOO", 200)

  describe("CoreGet") {

    val coreGet: CoreGet = new CoreGet {
      override def get(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = {
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass1))))
      }
      override def get(url: String, queryParams: Seq[(String, String)])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass2))))
    }
    
    it("should GET from url and convert the response") {
      coreGet.GET[TestClass]("http://some.url").futureValue shouldBe testClass1
    }

    it("should GET from url with query parameters and convert the response") {
      coreGet.GET[TestClass]("http://some.url", Seq("some" -> "queryParam")).futureValue shouldBe testClass2
    }

  }
  
  describe("CorePatch") {

    val corePatch: CorePatch = new CorePatch {
      override def patch[I](url: String, body: I)(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass1))))
    }
    val testRequestObject = TestRequestClass("a", 1)
    
    it("should Patch at url and convert the response") {
      corePatch.PATCH[TestRequestClass, TestClass]("http://some.url", testRequestObject).futureValue shouldBe testClass1
    }
  }

  describe("CorePut") {
    val corePut: CorePut = new CorePut {
      override def put[I](url: String, body: I)(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass1))))
    }
    
    val testRequestObject = TestRequestClass("a", 1)
    
    it("should Put to url and convert the response") {
      corePut.PUT[TestRequestClass, TestClass]("http://some.url", testRequestObject).futureValue shouldBe testClass1
    }
  }
  
  describe("CoreDelete") {
    val coreDelete: CoreDelete = new CoreDelete {
      override def delete(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass1))))
    }
    
    it("should Delete from url and convert the response") {
      coreDelete.DELETE[TestClass]("http://some.url").futureValue shouldBe testClass1
    }
  }
  describe("CorePost") {
    val testClass3 = TestClass("FOO", 300)
    val testClass4 = TestClass("FOO", 400)

    val testRequestObject = TestRequestClass("a", 1)

    val stringBody = "string-body"
    val formBody = Map.empty[String, Seq[String]]

    val corePost: CorePost = new CorePost {

      override def post[I](url: String, body: I, headers: Seq[(String, String)])(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass1))))

      override def postString(url: String, body: String, headers: Seq[(String, String)])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = {

        Future.successful(
          if (body == stringBody )
            HttpResponse(200, Some(Json.toJson(testClass2)))
          else
            HttpResponse(404)
        )
      }


      override def postForm(url: String, body: Map[String, Seq[String]])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(
          if(body == formBody)
            HttpResponse(200, Some(Json.toJson(testClass3)))
          else
            HttpResponse(404)
        )


      override def postEmpty(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] =
        Future.successful(HttpResponse(200, Some(Json.toJson(testClass4))))

    }
    
    it("should Post to url and convert the response") {
      corePost.POST[TestRequestClass, TestClass]("http://some.url", testRequestObject).futureValue shouldBe testClass1
    }

    it("should Post string to url and convert the response") {
      corePost.POSTString[TestClass]("http://some.url", stringBody).futureValue shouldBe testClass2
    }

    it("should Post form to url and convert the response") {
      corePost.POSTForm[TestClass]("http://some.url", formBody).futureValue shouldBe testClass3
    }
    
    it("should Post empty to url and convert the response") {
      corePost.POSTEmpty[TestClass]("http://some.url").futureValue shouldBe testClass4
    }
  }

}
