package com.maju.rest.request.delete

import com.maju.rest.client.RestClient
import com.maju.rest.request.get.GetRequest
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification

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
    ): RequestSpecification {
        getRequestHandler.onRequestCreate(requestSpecification, request)
        return requestSpecification.apply {
            applyDelete(request)
        }

    }

    private fun RequestSpecification.applyDelete(request: IDeleteRequest) {
        if (request.contentType != null) contentType(request.contentType)
    }
}
