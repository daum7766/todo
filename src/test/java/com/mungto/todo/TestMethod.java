package com.mungto.todo;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public enum TestMethod {
    GET {
        @Override
        public ExtractableResponse<Response> extractedResponse(RequestSpecification request,
            String url) {
            return request.when()
                .get(url)
                .then()
                .extract();
        }
    },
    POST {
        @Override
        public ExtractableResponse<Response> extractedResponse(RequestSpecification request,
            String url) {
            return request.when()
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .post(url)
                .then()
                .extract();
        }
    },
    PUT {
        @Override
        public ExtractableResponse<Response> extractedResponse(RequestSpecification request,
            String url) {
            return request.when()
                .put(url)
                .then()
                .extract();
        }
    },
    DELETE {
        @Override
        public ExtractableResponse<Response> extractedResponse(RequestSpecification request,
            String url) {
            return request.when()
                .delete(url)
                .then()
                .extract();
        }
    };

    public abstract ExtractableResponse<Response> extractedResponse(RequestSpecification request,
        String url);
}