package api.apiTest;

import api.factoryRequest.FactoryRequest;
import api.factoryRequest.RequestInfo;
import api.utils.Properties;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class UserProject {
    RequestInfo requestInfo = new RequestInfo();
    Response response;

    String auth;
    JSONObject projectBody = new JSONObject();
    JSONObject userBody = new JSONObject();

    @BeforeEach
    public void setup(){
        userBody.put("FullName", "Natalia");
        userBody.put("Email", "bilbaonatalia" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")) + "190997@gmail.com");
        userBody.put("Password", "12345");
        projectBody.put("Content", "Natalia Project" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
    }

    @Test
    public void verifyUserProjectTest(){
        requestInfo.setHost(Properties.host + "api/user.json").setBody(userBody.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        auth = Base64.getEncoder().encodeToString((userBody.get("Email") + ":" + userBody.get("Password")).getBytes());

        requestInfo.setHost(Properties.host + "api/user.json").setBody(projectBody.toString()).setHeader("Authorization", "Basic" + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Content", equalTo(projectBody.get("Content")));

    }
}
