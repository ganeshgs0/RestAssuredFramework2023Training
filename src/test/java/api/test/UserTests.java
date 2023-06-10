package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
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
	}
	
	@Test(priority=1)
	public void test_postUser() {
		     Response response=UserEndpoints.createUser(userPayload);
		     response.then().log().all();
		     
		     Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority=2)
	public void testGetUserByName() {
		Response response=UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response=UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
		//response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseAferUpdate=UserEndpoints.readUser(this.userPayload.getUsername());
		responseAferUpdate.then().log().all();
		System.out.println("printing after update"+responseAferUpdate.jsonPath().get("email"));
		System.out.println("getting value from faker"+this.userPayload.getEmail());
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		Response response=UserEndpoints.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
}
