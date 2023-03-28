package co.edu.uniquindio.ingesis.microservicios.test.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public @Data class PagoDTO {
	
	private String clienteId;
	private String metodoPagoId;
	private Double monto;
	private String refVenta;
	private Integer estadoId;
	private String voucherId;
	
}
