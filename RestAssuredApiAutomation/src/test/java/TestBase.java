import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    @BeforeEach
    public void init(){
        RestAssured.baseURI = "http://127.0.0.1:6040";
        RestAssured.port = 6039;
        RestAssured.basePath = "/mongo";
    }

}
