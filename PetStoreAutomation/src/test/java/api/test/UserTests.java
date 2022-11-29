package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;


public class UserTests {
    Faker faker;
    User userPayload;
    public Logger logger; //for logs

    //before tests start we generate a data (payload)
    @BeforeClass
    public void setup() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger = LogManager.getLogger(this.getClass()); //init logger

    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("***** Creating user *****");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("***** User is created *****");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("***** Reading user info *****");
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("***** User info is displayed *****");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {

        logger.info("***** Updating user *****");

        userPayload.setFirstName("Taras");
        userPayload.setLastName("Shevchenko");
        userPayload.setEmail("sheva@gmail.com");

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("***** User updated *****");
        //Checking user data after update
        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
        responseAfterUpdate.prettyPrint();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("***** Deleting user *****");
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("***** User is deleted *****");
    }
}
