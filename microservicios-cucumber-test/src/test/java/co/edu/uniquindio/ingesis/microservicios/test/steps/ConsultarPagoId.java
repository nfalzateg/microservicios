package co.edu.uniquindio.ingesis.microservicios.test.steps;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import co.edu.uniquindio.ingesis.microservicios.test.dtos.PagoDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ConsultarPagoId {
	
	private static final String BASE_URL = "http://localhost:8080/pago";
	private Response response;
	private PagoDTO pagoDTO = new PagoDTO();
	private PagoDTO pagoDTOResponse;
	private ArrayList<String> refVentas = new ArrayList<>();
	private Integer id;

	@Given("Soy un cliente con un token valido")
	public void soyUnUsuarioConUnTokenValido() throws Throwable {
		
		
		
		//pagoDTO = PagoDTO.builder().clienteId("5602").metodoPagoId("3").monto(50000.00).refVenta("JKasaihsLDSO7").build();
	}
	
	@When("usando el id de pago {int}")
	public void usandoElIdDePago(int id) throws Throwable {
		this.id = id;
		
	}
	
	@And("invoco el servicio de consultar pago")
	public void invocoElServicioDeConsultarPago() throws Throwable {
		baseURI = BASE_URL;
		
		//Respuesta del consumo del servicio
		response = given().log().all()
			       .pathParam("id", this.id)
			       .when()
			       .get("http://localhost:8080/pago/{id}");
	}
	
	@Then("obtengo un codigo de estado {int}")
	public void obtengoUnCodigoDeEstado(int status) {
		response.then().statusCode(status);
	}
	
	@And("informacion del pago {string}")
	public void informacionDelPago(String valido) throws Throwable {
		
		try {
			pagoDTOResponse = response.then()
	                .and()
	                .extract().body().as(PagoDTO.class);
		} catch (Exception e) {
			assertEquals("false", valido);
		}
		
		if(pagoDTOResponse != null) {
			assertEquals("true", valido);
		}
		else {
			assertEquals("false", valido);
		}
		
        //assertNotNull(pagoDTOResponse);
        //assertTrue(pagoDTOResponse.getVoucherId().equals("UHIAUSD8966asdD"));
        
	}
	

}
