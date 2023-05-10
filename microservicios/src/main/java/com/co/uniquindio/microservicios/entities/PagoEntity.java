package com.co.uniquindio.microservicios.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public @Data class PagoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;
	
	@Column(name = "fecha", updatable = false, nullable = false)
	private Date fecha;
	
	@Column(name = "cliente_id")
	private Integer clienteId;
	
	@Column(name = "metodo_pago_id")
	private Integer metodoPagoId;
	
	@Column(name = "monto")
	private Double monto;
	
	@Column(name = "referencia_venta")
	private String refVenta;
	
	@Column(name = "estado_id")
	private Integer estadoId;
	
	@Column(name = "vouncher_id")
	private String voucherId;

}
