package com.co.uniquindio.microservicios.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.uniquindio.microservicios.entities.Pago;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/pago")
@Tag(name = "PagoController", description = "API para manejo de pagos")
public class PagoController {
	
	private ArrayList<Pago> pagos;
	
	@Autowired
	public PagoController() {
		pagos = new ArrayList<Pago>();
		inicializarArreglosPagos();
	}
	
	private void inicializarArreglosPagos() {
		Pago pago1 = new Pago();
		pago1.setId(1);
		pago1.setFecha(new Timestamp( (new java.util.Date( ) ).getTime( ) ));
		pago1.setClienteId(5601);
		pago1.setMetodoPagoId(1);
		pago1.setMonto(50000.00);
		pago1.setRefVenta("JKasaihsLDSO7");
		pago1.setVoucherId("UHIAUSD8966asdD");
		pago1.setEstadoId(2);
		
		pagos.add(pago1);
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Pago pago) {
		
		System.out.println(pago.getMetodoPagoId());
		
		if(pago != null) {
			if(!validarMetodoDePago(pago.getMetodoPagoId())) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud, metodo de pago no valido");
			}
			
			if(!validarRefCompra(pago.getRefVenta())) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud, referncia no valida");
			}
			pago.setEstadoId(2);
			pago.setVoucherId("UHIAUSD8966asdD");
			 
			return ResponseEntity.status(HttpStatus.CREATED).body(pago);
			
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud");	 
		 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> consultarPafoId (@PathVariable("id") Integer id){
		
		if(id != null) {
			
			for (Pago pago : pagos) {
				if(pago.getId() == id) {
					return ResponseEntity.status(HttpStatus.OK).body(pago);
				}
			}
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el pago");
			
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud");	
		
	}
	
//	@PostMapping("/consultar-id/{id}")
//	public ResponseEntity<GstCategoria> findGstCategoria(@PathVariable("id") Long gstCategoriaId, @RequestBody GstCategoria gstCategoria, @RequestHeader Map<String, String> headers) {

	
	@GetMapping
    public ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
	
	private boolean validarMetodoDePago(Integer metodoPago) {
		
		boolean valido = false;
		
		//Se valida el metodo de pago
		if(metodoPago == 1 || metodoPago == 2 || metodoPago == 3) {
			valido = true;
		}
		
		return valido;
	}
	

	private boolean validarRefCompra(String refCompra) {
		
		//Se valida que el primer caracter sea una J y que su tamaño se igual a 13 caracteres
		return (refCompra.charAt(0) == 'J' && refCompra.length() == 13);
	}

}
