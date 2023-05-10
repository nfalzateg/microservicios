package com.co.uniquindio.microservicios.util;

import java.io.Serializable;


public class ResponseJson implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigoError;
	
	private String mensajeError;
	
	private String resultado;
	
	private String mensaje;
	
	@Override
	public String toString() {
		String retorno = getClass().getCanonicalName() + " \n{" 
				+ "[codigoError=" + toStringObj(codigoError) + "] "
				+ "[mensajeError=" + toStringObj(mensajeError) + "] " 
				+ "[resultado=" + toStringObj(resultado) + "] " 
				+ "[mensaje=" + toStringObj(mensaje) + "] " 
				+ " \n}";
		return retorno;
	}
	
	protected String toStringObj(Object i) {
		return (i != null ? i.toString() : "");
	}
	
	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
