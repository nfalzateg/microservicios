package com.co.uniquindio.microservicios.models;

import java.util.Date;

import com.co.uniquindio.microservicios.util.ResponseJson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public @Data class Pago extends ResponseJson{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date fecha;
	private Integer clienteId;
	private Integer metodoPagoId;
	private Double monto;
	private String refVenta;
	private Integer estadoId;
	private String voucherId;
	
}
