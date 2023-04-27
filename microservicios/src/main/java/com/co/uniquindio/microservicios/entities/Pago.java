package com.co.uniquindio.microservicios.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public @Data class Pago {
	
	private Integer id;
	private Date fecha;
	private Integer clienteId;
	private Integer metodoPagoId;
	private Double monto;
	private String refVenta;
	private Integer estadoId;
	private String voucherId;
	
}
