package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void test_postUser() {
		
		logger.info("**** creating user***");
		     Response response=UserEndpoints2.createUser(userPayload);
		     response.then().log().all();
		     
		     Assert.assertEquals(response.getStatusCode(), 200);
		     
		     logger.info("****user_created***");
	}

	@Test(priority=2)
	public void testGetUserByName() {
		
		logger.info("**** getting user details***");
		Response response=UserEndpoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**** user details displayed***");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("*** updating user details**");
		
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response=UserEndpoints2.updateUser(this.userPayload.getUsername(), userPayload);
		//response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseAferUpdate=UserEndpoints2.readUser(this.userPayload.getUsername());
		responseAferUpdate.then().log().all();
		System.out.println("printing after update"+responseAferUpdate.jsonPath().get("email"));
		System.out.println("getting value from faker"+this.userPayload.getEmail());
		
		logger.info("***  user details updated***");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		 logger.info("deleting user details");
		 
		Response response=UserEndpoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***  user details deleted****");
		
	}
}
