package com.co.uniquindio.microservicios.mapper;

import com.co.uniquindio.microservicios.entities.MetodoPagoEntity;
import com.co.uniquindio.microservicios.models.MetodoPago;

public class MetodoPagoMapper {
	
	public MetodoPagoMapper() {
		super();
	}
	
	public static MetodoPagoEntity map(final MetodoPago metodoPago) {

		final MetodoPagoEntity metodoPagoEntity = new MetodoPagoEntity();

		metodoPagoEntity.setMetodoPagoId(metodoPago.getMetodoPagoId());
		metodoPagoEntity.setNombre(metodoPago.getNombre());
		metodoPagoEntity.setDescripcion(metodoPago.getDescripcion());

		return metodoPagoEntity;
	}

	public static MetodoPago map(final MetodoPagoEntity metodoPagoEntity) {

		MetodoPago metodoPago = new MetodoPago();

		metodoPago.setMetodoPagoId(metodoPagoEntity.getMetodoPagoId());
		metodoPago.setNombre(metodoPagoEntity.getNombre());
		metodoPago.setDescripcion(metodoPagoEntity.getDescripcion());

		return metodoPago;
	}

}
