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

package uk.gov.hmrc.play.test

import play.api.libs.json.Writes
import uk.gov.hmrc.http._

import scala.concurrent.{ExecutionContext, Future}

object Concurrent {
  import scala.concurrent.{Await, Future}
  import scala.concurrent.duration._

  val defaultTimeout = 5 seconds

  implicit def extractAwait[A](future: Future[A]) = await[A](future)
  implicit def liftFuture[A](v: A) = Future.successful(v)

  def await[A](future: Future[A]) = Await.result(future, defaultTimeout)
}

trait TestHttpCore extends CorePost with CoreGet with CorePut with CorePatch with CoreDelete {
  override def post[I](url: String, body: I, headers: Seq[(String, String)])(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def postString(url: String, body: String, headers: Seq[(String, String)])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def postForm(url: String, body: Map[String, Seq[String]])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def postEmpty(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def get(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def get(url: String, queryParams: Seq[(String, String)])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def put[I](url: String, body: I)(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def patch[I](url: String, body: I)(implicit wts: Writes[I], hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???

  override def delete(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = ???
}

