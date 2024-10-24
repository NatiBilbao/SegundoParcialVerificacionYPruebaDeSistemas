package apiToken.factoryRequest;

import apiToken.config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest{
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response=given()
                .auth()
                .preemptive()
                .basic(Configuration.user, Configuration.password)
                .body(requestInfo.getBody())
                .log()
                .all().
                when()
                .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }

    @Override
    public Response sendWithToken(RequestInfo requestInfo) {
        Response response=given()
                .header("Token", Configuration.tokenValue)
                .body(requestInfo.getBody())
                .log()
                .all()
                .when()
                .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
