package com.maju.rest.request.delete

import com.maju.rest.client.RestClient
import com.maju.rest.request.get.GetRequest
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification
import org.testcontainers.shaded.org.bouncycastle.asn1.ocsp.Request

interface IDeleteRequest : IGetRequest {
    var contentType: String?
}

class DeleteRequest : IDeleteRequest, GetRequest() {

    override var contentType: String? = null
}

class DeleteRequestHandler : RestClient.OnRequestCreateHandler<IDeleteRequest> {

    private val getRequestHandler = GetRequestHandler(false)

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IDeleteRequest
    ): RestResponse {
        getRequestHandler.onRequestCreate(requestSpecification, request)
        return requestSpecification.apply {
            applyDelete(request)
        }.let {
            RestResponse(it.delete(request.path))
        }

    }

    private fun RequestSpecification.applyDelete(request: IDeleteRequest) {
        contentType(request.contentType)
    }
}
