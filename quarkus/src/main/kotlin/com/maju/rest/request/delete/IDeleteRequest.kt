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

class DeleteRequest(path: String) : IDeleteRequest, GetRequest(path) {

    override var contentType: String? = null
}

class DeleteRequestHandler : RestClient.OnRequestCreateHandler<IDeleteRequest> {

    private val getRequestHandler = GetRequestHandler(false)

    override fun onRequestCreate(request: IDeleteRequest, port: Int): RequestSpecification {
        return getRequestHandler.onRequestCreate(request, port).apply {
            applyDelete(request)
        }

    }

    private fun RequestSpecification.applyDelete(request: IDeleteRequest) {
        if (request.contentType != null) contentType(request.contentType)
    }
}
