package com.co.uniquindio.microservicios.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public @Data class Pago {
	
	private String clienteId;
	private String metodoPagoId;
	private Double monto;
	private String refVenta;
	private Integer estadoId;
	private String voucherId;
	

}
