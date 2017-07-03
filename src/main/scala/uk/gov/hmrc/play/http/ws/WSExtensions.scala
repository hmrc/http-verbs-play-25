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

import play.api.libs.json.Writes
import uk.gov.hmrc.http.HttpVerbs.{DELETE => DELETE_VERB, GET => GET_VERB, PATCH => PATCH_VERB, POST => POST_VERB, PUT => PUT_VERB}
import uk.gov.hmrc.http.{CoreGet, HeaderCarrier, _}
import uk.gov.hmrc.play.http.HttpReads

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext


object WSExtensions {

  implicit class ExtendCoreGet(coreGet: CoreGet) {
    @deprecated("clients are encouraged to use the @see uk.gov.hmrc.play.http.CoreGet#get instead and do their own Reads")
    def GET[A](url: String)(implicit rds: HttpReads[A], hc: HeaderCarrier): Future[A] = {
      coreGet.get(url).map(response => rds.read(GET_VERB, url, response))
    }

    def GET[A](url: String, queryParams: Seq[(String, String)])(implicit rds: HttpReads[A], hc: HeaderCarrier): Future[A] = {
      coreGet.get(url, queryParams).map(response => rds.read(GET_VERB, url, response))
    }
  }

  implicit class ExtendCorePatch(corePatch: CorePatch) {
    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePatch.patch instead and do their own Reads")
    def PATCH[I, O](url: String, body: I)(implicit wts: Writes[I], rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePatch.patch(url, body).map(response => rds.read(PATCH_VERB, url, response))
    }
  }

  implicit class ExtendCorePut(corePut: CorePut) {
    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePut.patch instead and do their own Reads")
    def PUT[I, O](url: String, body: I)(implicit wts: Writes[I], rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePut.put(url, body).map(response => rds.read(PUT_VERB, url, response))
    }
  }

  implicit class ExtendCoreDelete(coreDelete: CoreDelete) {
    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePut.delete instead and do their own Reads")
    def DELETE[O](url: String)(implicit rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      coreDelete.delete(url).map(response => rds.read(DELETE_VERB, url, response))
    }
  }


  implicit class ExtendCorePost(corePost: CorePost) {
    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePost.post instead and do their own Reads")
    def POST[I, O](url: String, body: I, headers: Seq[(String,String)] = Seq.empty)(implicit wts: Writes[I], rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePost.post(url, body, headers).map(rds.read(POST_VERB, url, _))
    }

    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePost.postString instead and do their own Reads")
    def POSTString[O](url: String, body: String, headers: Seq[(String,String)] = Seq.empty)(implicit rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePost.postString(url, body, headers).map(rds.read(POST_VERB, url, _))
    }

    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePost.postForm instead and do their own Reads")
    def POSTForm[O](url: String, body: Map[String, Seq[String]])(implicit rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePost.postForm(url, body).map(rds.read(POST_VERB, url, _))
    }


    @deprecated("clients are encouraged to use the uk.gov.hmrc.play.http.CorePost.postEmpty instead and do their own Reads")
    def POSTEmpty[O](url: String)(implicit rds: HttpReads[O], hc: HeaderCarrier): Future[O] = {
      corePost.postEmpty(url).map(rds.read(POST_VERB, url, _))
    }
  }
}
