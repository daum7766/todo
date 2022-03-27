package com.mungto.todo;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @RegisterExtension
    final RestDocumentationExtension restDocumentationExtension = new RestDocumentationExtension(
        "build/generated-snippets");
    protected RequestSpecification spec;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {
        RestAssured.port = port;
        spec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentationContextProvider))
            .build();
    }

    protected ExtractableResponse<Response> makeResponse(String url, TestMethod testMethod,
        String identifier) {
        return makeResponse(url, testMethod, identifier, null);
    }

    protected ExtractableResponse<Response> makeResponse(String url, TestMethod testMethod,
        String identifier, Object requestBody) {
        return makeResponse(url, testMethod, identifier, requestBody, null);
    }

    protected ExtractableResponse<Response> makeResponse(String url, TestMethod testMethod,
        String identifier, Object requestBody, String token) {
        RequestSpecification request = RestAssured.given(this.spec)
            .filter(
                document(identifier,
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (Objects.nonNull(requestBody)) {
            request = request.body(requestBody);
        }
        return testMethod.extractedResponse(request, url);
    }
}
