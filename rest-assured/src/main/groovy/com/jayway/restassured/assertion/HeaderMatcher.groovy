/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jayway.restassured.assertion

import com.jayway.restassured.exception.AssertionFailedException
import org.hamcrest.Matcher

class HeaderMatcher {

  def headerName
  def Matcher<String> matcher

  def containsHeader(headers) {
    def value = getHeaderValueOrThrowExceptionIfHeaderIsMissing(headerName, headers)
    if(!matcher.matches(value)) {
      throw new AssertionFailedException("Expected header \"$headerName\" was not $matcher, was \"$value\".")
    }
  }

  private def getHeaderValueOrThrowExceptionIfHeaderIsMissing(headerName, headers) {
    def header = headers.get(headerName)
    if (header == null) {
      String headersAsString = "";
      headers.each { headersAsString += "\n$it.key: $it.value" }
      throw new AssertionFailedException("Header \"$headerName\" was not defined in the response. Headers are: $headersAsString");
    }
    return header
  }
}
