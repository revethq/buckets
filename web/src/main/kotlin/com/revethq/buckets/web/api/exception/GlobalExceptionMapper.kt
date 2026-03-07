package com.revethq.buckets.web.api.exception

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GlobalExceptionMapper : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        if (exception is WebApplicationException) {
            return exception.response
        }

        val message =
            when (exception) {
                is IllegalArgumentException -> exception.message ?: "Invalid argument"
                is IllegalStateException -> exception.message ?: "Invalid state"
                else -> "An error occurred"
            }

        val status =
            when (exception) {
                is IllegalArgumentException -> Response.Status.BAD_REQUEST
                is IllegalStateException -> Response.Status.CONFLICT
                else -> Response.Status.INTERNAL_SERVER_ERROR
            }

        return Response
            .status(status)
            .entity(mapOf("error" to message, "type" to exception.javaClass.simpleName))
            .build()
    }
}
