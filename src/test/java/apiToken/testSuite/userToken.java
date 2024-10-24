package apiToken.testSuite;

import apiToken.config.Configuration;
import apiToken.factoryRequest.FactoryRequest;
import apiToken.factoryRequest.RequestInfo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class userToken extends TestBase{

    @Test
    public void createUserTokenProjectDeleteTokenCreateProject(){
        String user = "nataliabilbaocano27@gmail.com";
        String pass = "Admin123.";

        JSONObject body = new JSONObject();
        body.put("Email", user);
        body.put("FullName", "Natalia Bilbao Cano");
        body.put("Password", pass);

        this.createUser(Configuration.host + "/api/user.json", body, post);

        Configuration.setUser(user);
        Configuration.setPassword(pass);
        int idUser = response.then().extract().path("Id");

        this.createToken();

        JSONObject item = new JSONObject();
        item.put("Content", "Item creado");
        this.createItem(Configuration.host + "/api/user.json", item, post);

        this.deleteToken();

        item.put("Content", "Item nuevo");
        this.createItemWrong(Configuration.host + "/api/user.json", item, post);
        }

        private void createUser(String host, JSONObject body, String post){
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200)
                .body("Email", equalTo(body.get("Email")));
    }

    private void createItem(String host, JSONObject body, String post){
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).sendWithToken(requestInfo);
        response.then().statusCode(200)
                .body("Content", equalTo(body.get("Content")));
    }

    private void createItemWrong(String host, JSONObject body, String post){
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).sendWithToken(requestInfo);
        response.then().statusCode(200)
                .body("ErrorCode", equalTo(102));
    }

    private void createToken(){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUrl(Configuration.token);
        response = FactoryRequest.make(get).send(requestInfo);
        Configuration.tokenValue = response.then()
                .extract()
                .path("TokenString");
    }

    private void deleteToken(){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUrl(Configuration.token);
        response = FactoryRequest.make(delete).send(requestInfo);
        Configuration.tokenValue = "";
    }
}
