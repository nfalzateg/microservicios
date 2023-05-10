package co.edu.uniquindio.ingesis.microservicios.test.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonIgnoreProperties
public @Data class PagoDTO {
	
	private Integer id;
	private Date fecha;
	private Integer clienteId;
	private Integer metodoPagoId;
	private Double monto;
	private String refVenta;
	private Integer estadoId;
	private String voucherId;
	private String codigoError;
	
	private String mensajeError;
	
	private String resultado;
	
	private String mensaje;
	
}
