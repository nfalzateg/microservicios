package com.co.uniquindio.microservicios.mapper;

import com.co.uniquindio.microservicios.entities.PagoEntity;
import com.co.uniquindio.microservicios.models.Pago;

public class PagoMapper {
	
	public PagoMapper() {
		super();
	}
	
	public static PagoEntity map(final Pago pago) {

		final PagoEntity pagoEntity = new PagoEntity();

		pagoEntity.setId(pago.getId());
		pagoEntity.setFecha(pago.getFecha());
		pagoEntity.setClienteId(pago.getClienteId());
		pagoEntity.setMetodoPagoId(pago.getMetodoPagoId());
		pagoEntity.setMonto(pago.getMonto());
		pagoEntity.setRefVenta(pago.getRefVenta());
		pagoEntity.setEstadoId(pago.getEstadoId());
		pagoEntity.setVoucherId(pago.getVoucherId());

		return pagoEntity;
	}

	public static Pago map(final PagoEntity pagoEntity) {

		Pago pago = new Pago();

		pago.setId(pagoEntity.getId());
		pago.setFecha(pagoEntity.getFecha());
		pago.setClienteId(pagoEntity.getClienteId());
		pago.setMetodoPagoId(pagoEntity.getMetodoPagoId());
		pago.setMonto(pagoEntity.getMonto());
		pago.setRefVenta(pagoEntity.getRefVenta());
		pago.setEstadoId(pagoEntity.getEstadoId());
		pago.setVoucherId(pagoEntity.getVoucherId());

		return pago;
	}

}
