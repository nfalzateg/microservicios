package co.edu.uniquindio.ingesis.microservicios.test.steps;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import co.edu.uniquindio.ingesis.microservicios.test.dtos.PagoDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CrearPagoSteps {

	private static final String BASE_URL = "http://localhost:8080/pago";
	private Response response;
	private PagoDTO pagoDTO = new PagoDTO();
	private PagoDTO pagoDTOResponse;
	//private ArrayList<String> refVentas = new ArrayList<>();

	@Given("Soy un usuario con un token valido")
	public void soyUnUsuarioConUnTokenValido() throws Throwable {
		
		
		
		pagoDTO = PagoDTO.builder().clienteId(5602).monto(10000.00).build();
	}

	@And("invoco el servicio de crear pago")
	public void invocoElServicioDeCrearPago() throws Throwable {
		baseURI = BASE_URL;
		
		//Respuesta del consumo del servicio
		response = given().contentType(ContentType.JSON).body(pagoDTO).when().post();
	}
	
	@When("usando el metodo de pago {int}")
	public void usandoElMetodoDePago(int metodoPago) throws Throwable {
		pagoDTO.setMetodoPagoId(metodoPago);
		
	}
	
	@And("usando la referencia compra {string}")
	public void usandoLaReferenciaCompra(String refCompra) throws Throwable {
		pagoDTO.setRefVenta(refCompra);
		
	}

	@Then("obtengo un status code {int}")
	public void obtengoUnStatusCode(int status) {
		response.then().statusCode(status);
	}

	@And("voucher de verificacion")
	public void voucherDeVerificacion() throws Throwable {
		pagoDTOResponse = response.then()
                .body("voucherId",response->notNullValue())
                .and()
                .extract().body().as(PagoDTO.class);
		
		
        assertNotNull(pagoDTOResponse);
        //assertTrue(pagoDTOResponse.getVoucherId().equals("UHIAUSD8966asdD"));
        
	}

}
