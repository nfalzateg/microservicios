package com.co.uniquindio.microservicios.models;

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
public @Data class MetodoPago extends ResponseJson{
	
	private static final long serialVersionUID = 1L;
	private Integer metodoPagoId;
	private String nombre;
	private String descripcion;
	
}
