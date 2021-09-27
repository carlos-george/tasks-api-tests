package br.com.til.tasks.api.tests;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	
	private static final int NAD_REQUEST = 400;
	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CODE_CREATED = 201;
	
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefa() {
		
		RestAssured
			.given()
			.when()
				.get("/todo")
			.then()
				.statusCode(STATUS_CODE_OK);
		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		sb.append("\"task\":\"Test Api way\",");
		sb.append("\"dueDate\":\"2021-10-20\"");
		sb.append("}");
		
		RestAssured
			.given()
				.body(sb.toString()).contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(STATUS_CODE_CREATED);
		
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		sb.append("\"task\":\"Test Api way\",");
		sb.append("\"dueDate\":\"2020-10-20\"");
		sb.append("}");
		
		RestAssured
		.given()
			.body(sb.toString()).contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(NAD_REQUEST)
			.body("message", CoreMatchers.is("Due date must not be in past"));
		
	}
}
