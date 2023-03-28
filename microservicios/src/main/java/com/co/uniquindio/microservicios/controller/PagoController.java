package com.co.uniquindio.microservicios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.uniquindio.microservicios.entities.Pago;

@RestController
@RequestMapping(path = "/pago")
public class PagoController {

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
		else {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud");
		}
		
		 
		 
		 
	}
	
	@GetMapping
    public ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
	
	private boolean validarMetodoDePago(String metodoPago) {
		
		boolean valido = false;
		
		//Se valida el metodo de pago
		if(metodoPago.equals("1") || metodoPago.equals("2") || metodoPago.equals("3")) {
			valido = true;
		}
		
		return valido;
	}
	

	private boolean validarRefCompra(String refCompra) {
		
		//Se valida que el primer caracter sea una J y que su tamaño se igual a 13 caracteres
		return (refCompra.charAt(0) == 'J' && refCompra.length() == 13);
	}

}