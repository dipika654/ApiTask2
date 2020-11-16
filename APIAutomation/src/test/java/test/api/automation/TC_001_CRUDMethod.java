package test.api.automation;

//import java.io.IOException;
import org.api.library.*;
import org.testng.Assert;
import org.testng.annotations.Test;

//import org.testng.annotations.Test;
//import org.testng.Assert;
import static com.jayway.restassured.RestAssured.*;
//import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
//import com.jayway.restassured.response.ValidatableResponseOptions;
import com.jayway.restassured.http.ContentType;

//import junit.framework.Assert;

public class TC_001_CRUDMethod {

	@Test
	//public static void main(String[] args) throws IOException
	 public void tc_001()
	 {
		int input = 43;
		
		//Step 1: Created new Resources 
		
		CreatePostClass cpost = new CreatePostClass();
		cpost.setId(input);
		cpost.setTitle("Title1");
		cpost.setAuthor("Author1");
		
		
		ValidatableResponse response =  (ValidatableResponse) given()
		.contentType(ContentType.JSON)
		.body(cpost)
		
		.when()
		.post("http://localhost:3000/posts")
		.then()
		 .contentType(ContentType.JSON);
		 
		int responseId = response.extract().path("id");
		int actualStatusCode = response.extract().response().getStatusCode();
		 
		 Assert.assertEquals(actualStatusCode, 201);
		 
		 // Get Request : Validating the response 
		 
		 ValidatableResponse response1 = (ValidatableResponse) when()
		.get("http://localhost:3000/posts/"+responseId)
		.then()
        .contentType(ContentType.JSON);
		 String actTitle = response1.extract().path("title");
		 String actauthor = response1.extract().path("author");
		 
		 Assert.assertEquals(actTitle, "Title1");
		 Assert.assertEquals(actauthor, "Author1");
		 
		 //Step 3: Update Resource
		 
		 CreatePostClass cpost1 = new CreatePostClass();
		 cpost1.setId(input);
		 cpost1.setTitle("Updated Title");
		 cpost1.setAuthor("Updated Author");
		 
		 ValidatableResponse response2 = given()
		 .contentType(ContentType.JSON)
		 .body(cpost1)
		 .when()
		 .put("http://localhost:3000/posts/"+input)
		 .then()
		 .contentType(ContentType.JSON);
		 
		 //Validating the data is updated or not
		 
				 ValidatableResponse response4 = (ValidatableResponse) when()
				.get("http://localhost:3000/posts/"+responseId)
				.then()
		        .contentType(ContentType.JSON);
				 String actTitle1 = response4.extract().path("title");
				 String actauthor1 = response4.extract().path("author");
				 
				 Assert.assertEquals(actTitle1, "Updated Title");
				 Assert.assertEquals(actauthor1, "Updated Author");
		 
		 
		 //Step 4: Delete the recourse
				 when()
				 .delete("http://localhost:3000/posts/"+input);
				 
				 //Validating the data is deleted or not
				 
				 ValidatableResponse response6 = (ValidatableResponse) when()
				.get("http://localhost:3000/posts/"+responseId)
				.then()
		        .contentType(ContentType.JSON);
				 
				 Assert.assertNotEquals(response6.extract().response().statusCode(),201);
				
				 
				 
		 
		 
	 }

	
		

}