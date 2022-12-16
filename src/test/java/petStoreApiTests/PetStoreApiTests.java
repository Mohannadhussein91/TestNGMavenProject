package petStoreApiTests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreApiTests {
	
	
	int catID;
	int petID;
	
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		
	}
	
	
	@Test (dependsOnMethods= "postApet")
	public void getPetById() {

		RestAssured.given().accept(ContentType.JSON).get("/pet/30061991").then()
				.statusCode(200);
	}
	

	@Test
	public void findPetByStatus() {
		// i can use also queryParam instead of param
		RestAssured.given().accept(ContentType.JSON).contentType("application/json").param("status", "pending").when()
				.get("/pet/findByStatus").then().statusCode(200)
				.contentType("application/json");
	}

	@Test (dependsOnMethods = {"postACat" , "updatACat"})
	public void getById() {
		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.get("/pet/1991");

		myResponse.prettyPrint();
		// verifying the status code
		// by doing the assert that is just we add readability and also the same thing
		myResponse.then().assertThat().statusCode(200).and().contentType("application/json");

		String petName = myResponse.path("name");
		System.out.println("Pet Name is :" + petName);
		Assert.assertEquals(petName, "Taco");

		int petId = myResponse.path("id");
		System.out.println("Pet id is : " + petId);
		Assert.assertEquals(petId, 1991);

		int tagsId = myResponse.path("tags[0].id");
		System.out.println("Pet tag number is : " + tagsId);
		Assert.assertEquals(tagsId, 18);

		String tagsName2 = myResponse.path("tags[1].name");
		System.out.println("Pet Second name is : " + tagsName2);
		Assert.assertEquals(tagsName2, "Anatolian");

		// there is another way by using jsonPath function just we add Get here
		String categoryName = myResponse.jsonPath().get("category.name"); // category here is not array
		System.out.println("Category Name is : " + categoryName);
		Assert.assertEquals(categoryName, "cat");
		
		String catStatus = myResponse.body().jsonPath().get("status");
		Assert.assertEquals(catStatus, "pending");

	}

	

	@Test
	public void postACat() {

		String requestBody = "{\n"
				+ "    \"id\": 1991,\n"
				+ "    \"category\": {\n"
				+ "      \"id\": 21,\n"
				+ "      \"name\": \"cat\"\n"
				+ "    },\n"
				+ "    \"name\": \"Ember\",\n"
				+ "    \"photoUrls\": [\n"
				+ "      \"string\"\n"
				+ "    ],\n"
				+ "    \"tags\": [\n"
				+ "      {\n"
				+ "        \"id\": 18,\n"
				+ "        \"name\": \"persian\"\n"
				+ "      },\n"
				+ "      {\n"
				+ "        \"id\": 2,\n"
				+ "        \"name\": \"Anatolian\"\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"status\": \"available\"\n"
				+ "  }";

		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(requestBody).when()
				.post("/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");
		myResponse.prettyPrint();
		
		catID = myResponse.jsonPath().get("id");
		

	}
	
	
	// update the cat status to pending
	@Test (dependsOnMethods = "postACat")
	public void updatACat() {
		String catRequestBody = "{\n"
				+ "    \"id\": 1991,\n"
				+ "    \"category\": {\n"
				+ "      \"id\": 21,\n"
				+ "      \"name\": \"cat\"\n"
				+ "    },\n"
				+ "    \"name\": \"Taco\",\n"
				+ "    \"photoUrls\": [\n"
				+ "      \"string\"\n"
				+ "    ],\n"
				+ "    \"tags\": [\n"
				+ "      {\n"
				+ "        \"id\": 18,\n"
				+ "        \"name\": \"persian\"\n"
				+ "      },\n"
				+ "      {\n"
				+ "        \"id\": 2,\n"
				+ "        \"name\": \"Anatolian\"\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"status\": \"pending\"\n"
				+ "  }";
		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(catRequestBody)
		.when().put("/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(myResponse.body().jsonPath().get("status"), "pending");
		
		
	}
	
	
	@Test
	public void postApet() {

		String requestBody = "{\n"
				+ "    \"id\": 30061991,\n"
				+ "    \"category\": {\n"
				+ "      \"id\": 21,\n"
				+ "      \"name\": \"cat\"\n"
				+ "    },\n"
				+ "    \"name\": \"Loco\",\n"
				+ "    \"photoUrls\": [\n"
				+ "      \"string\"\n"
				+ "    ],\n"
				+ "    \"tags\": [\n"
				+ "      {\n"
				+ "        \"id\": 18,\n"
				+ "        \"name\": \"persian\"\n"
				+ "      },\n"
				+ "      {\n"
				+ "        \"id\": 2,\n"
				+ "        \"name\": \"Anatolian\"\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"status\": \"available\"\n"
				+ "  }";

		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(requestBody).when()
				.post("/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");
		
		myResponse.prettyPrint();
		
		// storing the pet id in a global variable (instance variable)
		
		petID = myResponse.jsonPath().get("id");

	}
	
	
	public void deleteThePet() {
		Response deleteResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").when()
				.delete("/pet/"+ petID);
		
		deleteResponse.then().statusCode(200).contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), String.valueOf(petID));
	}
	
	
	public void deleteTheCat() {
		Response deleteResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").when()
				.delete("/pet/"+ catID);
		
		deleteResponse.then().statusCode(200).contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), String.valueOf(catID));
	}
	
	
	
	// create a cat with request body in JSON file - example 
	@Test
	public void createCatWithJsonFile() {
		
		File catRequestBodyFile = new File("./src/test/resources/JsonTestData/createCat.json");
		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(catRequestBodyFile).when()
				.post("/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");
		
		myResponse.prettyPrint();
		
		catID = myResponse.jsonPath().getInt("id");
		
	}
	
	// RestAssured chain validation
	@Test
	public void chainValidation() {
		File catRequestBodyFile = new File("./src/test/resources/JsonTestData/createCat.json");
		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(catRequestBodyFile).when()
				.post("/pet");
		
		
		myResponse.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", equalTo(11223344))
		.and().assertThat().body("category.id", equalTo(21))
		.and().assertThat().body("name", equalTo("Momo"))
		.and().assertThat().body("tags[0].id", equalTo(18))
		.and().assertThat().body("tags[0].name", equalTo("persian"))
		.and().assertThat().body("tags[1].id", equalTo(2))
		.and().assertThat().body("tags[1].name",equalTo("Anatolian"))
		.and().assertThat().body("status", equalTo("available"));
		
		myResponse.prettyPrint();
		catID = myResponse.jsonPath().getInt("id");
		
		
	}
	
	
	@AfterTest (enabled = true)
	public void cleanUp() {
		deleteTheCat();
		deleteThePet();
	}
	
	// negative test case
	@Test
	public void invalidIdUpdateCat() {
		
		String catRequestBody = "{\n"
				+ "    \"id\": '1991',\n"
				+ "    \"category\": {\n"
				+ "      \"id\": 21,\n"
				+ "      \"name\": \"cat\"\n"
				+ "    },\n"
				+ "    \"name\": \"Ember\",\n"
				+ "    \"photoUrls\": [\n"
				+ "      \"string\"\n"
				+ "    ],\n"
				+ "    \"tags\": [\n"
				+ "      {\n"
				+ "        \"id\": 18,\n"
				+ "        \"name\": \"persian\"\n"
				+ "      },\n"
				+ "      {\n"
				+ "        \"id\": 2,\n"
				+ "        \"name\": \"Anatolian\"\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"status\": \"pending\"\n"
				+ "  }";
		Response myResponse = RestAssured.given().accept(ContentType.JSON)
				.contentType("application/json").body(catRequestBody)
		.when().put("https://petstore.swagger.io/v2/pet");
		
		myResponse.then().statusCode(400).and().contentType("application/json");
		Assert.assertEquals(myResponse.body().jsonPath().get("message"), "bad input");
		
		
	
		
	}
}
