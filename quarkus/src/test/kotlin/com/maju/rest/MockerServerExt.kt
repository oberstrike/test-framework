package com.maju.rest

import org.mockserver.client.ForwardChainExpectation
import org.mockserver.client.MockServerClient
import org.mockserver.matchers.Times
import org.mockserver.model.Cookie
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.Parameter

fun request(path: String,
            method: String = "GET",
            body: String = "",
            queryParameters: List<Pair<String, String>> = emptyList(),
            headers: List<Pair<String, String>> = emptyList(),
            cookies: List<Pair<String, String>> = emptyList()): HttpRequest =
    HttpRequest.request(path)
        .withMethod(method)
        .withBody(body)
        .withQueryStringParameters(queryParameters.map { (name, value) -> Parameter(name, value) })
        .withHeaders(headers.map { (name, value) -> Header(name, value) })
        .withCookies(cookies.map { (name, value) -> Cookie(name, value) })

fun MockServerClient.given(httpRequest: HttpRequest,
                           times: Times = Times.unlimited()): ForwardChainExpectation =
    `when`(httpRequest, times)