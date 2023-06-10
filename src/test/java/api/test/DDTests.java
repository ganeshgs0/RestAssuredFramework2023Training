package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.*;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userid,String uname,String fname,String lname,
			String email,String pwd,String ph) {
		User userpayload=new User();
		userpayload.setId(Integer.parseInt(userid));
		userpayload.setUsername(uname);
		userpayload.setFirstname(fname);
		userpayload.setLastname(lname);
		userpayload.setEmail(email);
		userpayload.setPassword(pwd);
		userpayload.setPhone(ph);
		Response response=UserEndpoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2,dataProvider="Usernames",dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String username) {
		Response response=UserEndpoints.deleteUser(username);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
