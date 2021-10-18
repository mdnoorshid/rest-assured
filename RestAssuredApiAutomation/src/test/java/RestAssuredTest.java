import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import com.restassured.model.User;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredTest extends  TestBase {

    @DisplayName("Logging the response")
    @Test
    public void getRestAssuredResponseFromMongoDbCrud(){
        given().when().get("/findallusers").then().log()
                .all();

    }

    @DisplayName("validating 200 for get request")
    @Test
    public void validateRestAssuredMongoDbCrud(){
          given()
                  .log()
                  .headers()
                  .when().get("/findallusers").then()
                  .assertThat()
                  .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Create new user")
    @Test
    public void createNewUser(){
        given()
                .log()
                .body()
                .when()
                .contentType(ContentType.JSON)
                .when()
                .body(createUserObj(new User()))
                .post("/createuser")
                .then()
                .log()
                .status()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @DisplayName("getting response as json string")
    @Test
    public void getJsonResponse(){
        String response = given().when().get("/findallusers").asString();
        System.out.println(response);
    }

    @DisplayName("Taking the response and asserting the empId of second value from the array")
    @Test
    public void getJsonResponseRootElement(){
        String response = given().when().get("/findallusers").asString();
        List<?> list = JsonPath.read(response, "$");
        System.out.println("root element: "+list );
        assertEquals(1026,Integer.parseInt(JsonPath.read(response, "$[1].empId").toString()));
    }

    @Test
    public void checkResponseTime(){
        long l = given().when().get("/findallusers").timeIn(TimeUnit.MILLISECONDS);
        System.out.println("Response time:: "+l);
        given().when().get("/findallusers").then().time(lessThan(1l),TimeUnit.SECONDS);
     }


    public User createUserObj(User user){
        Faker faker = new Faker();
        user.setUserName(faker.name().username());
        user.setEmpId(faker.idNumber().hashCode());
        user.setEmailId(faker.internet().emailAddress());
        user.setPhoneNumber(8431250146L);
        return user;
    }


}
