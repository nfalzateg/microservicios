package com.co.uniquindio.microservicios.controller;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.uniquindio.microservicios.entities.PagoEntity;
import com.co.uniquindio.microservicios.mapper.PagoMapper;
import com.co.uniquindio.microservicios.models.MetodoPago;
import com.co.uniquindio.microservicios.models.Pago;
import com.co.uniquindio.microservicios.repository.PagoRepository;
import com.co.uniquindio.microservicios.services.MetodoPagoService;
import com.co.uniquindio.microservicios.services.PagoService;
import com.co.uniquindio.microservicios.util.ErrorMessage;
import com.co.uniquindio.microservicios.util.ResponseJson;
import com.co.uniquindio.microservicios.util.ServiceException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/pago")
@Tag(name = "PagoController", description = "API para manejo de pagos")
public class PagoController {

//	@Autowired
//	PagoRepository pagoRepository;

	private final PagoService pagoService;

	private final MetodoPagoService metodoPagoService;

	@Autowired
	public PagoController(final PagoService pagoService, final MetodoPagoService metodoPagoService) {
		this.pagoService = pagoService;
		this.metodoPagoService = metodoPagoService;
	}

	protected static Logger logger = LogManager.getLogger(PagoController.class);

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Pago pago) {

		try {
			if (pago != null) {

				if (!validarMetodoDePago(pago.getMetodoPagoId())) {

					//Pago responseJson = new Pago();
					
					ResponseJson responseJson = new ResponseJson();

					responseJson.setCodigoError("PG0002");
					responseJson.setMensajeError("Error en el cuerpo de la solicitud, metodo de pago no es valido");
					responseJson.setResultado("ERROR");
					//responseJson.setVoucherId("no efectuado");

					// return ResponseEntity.ok(responseJson);
					// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);

					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseJson);
				}

				if (!validarRefCompra(pago.getRefVenta())) {
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
							.body("Error en el cuerpo de la solicitud, referncia no valida");
				}
				
				pago = pagoService.save(pago);
				
				pago.setMensaje(MessageFormat.format("El {0} ha sido creado con exito", "Pago"));
				pago.setResultado("OK");

				return ResponseEntity.status(HttpStatus.CREATED).body(pago);

			}
			ErrorMessage errorMessage = new ErrorMessage("Error en el cuerpo de la solicitud");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);

		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.error(" Exception : (" + Thread.currentThread().getStackTrace()[1].getMethodName() + ") : "
						+ e.getMessage());

			throw ServiceException.internalError("Error : {0} ", e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> consultarPagoId(@PathVariable("id") Integer id) {

		if (logger.isDebugEnabled())
			logger.debug("ENTRA" + Thread.currentThread().getStackTrace()[1].getMethodName() + " /{id} : "
					+ (id != null ? id.toString() : ""));

		Pago response = new Pago();

		if (id == null) {
			String mensaje = MessageFormat.format("Error : {0} ", "No hay datos para realizar la operación");

			if (logger.isDebugEnabled())
				logger.error(mensaje);

			ResponseJson responseJson = new ResponseJson();

			responseJson.setMensajeError(mensaje);
			responseJson.setCodigoError("PG0001");
			responseJson.setResultado("ERROR");

			// return ResponseEntity.ok(responseJson);
			// return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en
			// el cuerpo de la solicitud");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no proporcionado en la petición");

		}

		Optional<Pago> res = null;

		try {
			//res = pagoRepository.findById(id).map(PagoMapper::map);
			res = pagoService.findById(id);

		} catch (DataAccessException e) {

			if (logger.isDebugEnabled())
				logger.error(" DataAccessException : (" + Thread.currentThread().getStackTrace()[1].getMethodName()
						+ ") : " + e.getMessage());

			String mensaje = MessageFormat.format("Error : {0} - detalle [ {1} : {2} ]",
					"Error de lectura en la base de datos", e.getMessage(), e.getMostSpecificCause().getMessage());

			ResponseJson responseJson = new ResponseJson();

			responseJson.setCodigoError("PG0002");
			responseJson.setMensajeError(mensaje);
			responseJson.setResultado("ERROR");

			// return ResponseEntity.ok(responseJson);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);

		} catch (Exception e) {

			if (logger.isDebugEnabled())
				logger.error(" Exception : (" + Thread.currentThread().getStackTrace()[1].getMethodName() + ") : "
						+ e.getMessage());

			throw ServiceException.internalError("Error : {0} ", e.getMessage());

		}

		if (res != null && res.isPresent()) {

			response = res.get();

			response.setMensaje("Se encontro el registro");
			response.setResultado("OK");

			if (logger.isDebugEnabled())
				logger.debug("SALE" + Thread.currentThread().getStackTrace()[1].getMethodName());

			return ResponseEntity.ok(response);

		} else {

			String mensaje = MessageFormat.format("No se encontro el registro de {0} con {1} : {2} !", "pago", "id",
					id);

			if (logger.isDebugEnabled())
				logger.error(" EmptyResultDataAccessException : " + mensaje);

			ResponseJson responseJson = new ResponseJson();

			responseJson.setCodigoError("PG0005");
			responseJson.setMensajeError(mensaje);
			responseJson.setResultado("ERROR");

			// ErrorMessage errorMessage = new ErrorMessage("No se pudo encontrar el pago
			// con ID " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);

		}

	}

	@GetMapping
	public ResponseEntity<?> consultarPagos() {
		
		List<Pago> res = null;
		
		try {
			res = pagoService.findAll();
			
		}catch (Exception e) {

			if (logger.isDebugEnabled())
				logger.error(" Exception : (" + Thread.currentThread().getStackTrace()[1].getMethodName() + ") : "
						+ e.getMessage());

			throw ServiceException.internalError("Error : {0} ", e.getMessage());

		}
		
		if ( res != null && res.size()>0 ) {
			
			return ResponseEntity.status(HttpStatus.OK).body(res);
			
		}
		else {
			ResponseJson responseJson = new ResponseJson();

			responseJson.setCodigoError("PG0006");
			responseJson.setMensajeError("No se encontraron registros");
			responseJson.setResultado("ERROR");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);
		}

//		List<Pago> list = new ArrayList<Pago>(0);
//
//		List<PagoEntity> listEntities = pagoRepository.findAll();
//		listEntities.forEach(entity -> list.add(PagoMapper.map(entity)));
//
//		return ResponseEntity.status(HttpStatus.OK).body(list);

	}

//	@PatchMapping("/{id}")
//	public ResponseEntity<?> retornarPagoId(@PathVariable("id") Integer id) {
//
//		if (logger.isDebugEnabled())
//			logger.debug("ENTRA" + Thread.currentThread().getStackTrace()[1].getMethodName() + " /{id} : "
//					+ (id != null ? id.toString() : ""));
//
//		Pago response = new Pago();
//
//		if (id == null) {
//			String mensaje = MessageFormat.format("Error : {0} ", "No hay datos para realizar la operación");
//
//			if (logger.isDebugEnabled())
//				logger.error(mensaje);
//
//			ResponseJson responseJson = new ResponseJson();
//
//			responseJson.setMensajeError(mensaje);
//			responseJson.setCodigoError("PG0001");
//			responseJson.setResultado("ERROR");
//
//			// return ResponseEntity.ok(responseJson);
//			// return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en
//			// el cuerpo de la solicitud");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no proporcionado en la petición");
//
//		}
//
//		Optional<PagoEntity> res = null;
//
//		try {
//			res = pagoRepository.findById(id);
//
//			if (res != null && res.isPresent()) {
//				PagoEntity pago = res.get();
//				pago.setEstadoId(3);
//
//				Pago pagoRes = PagoMapper.map(pagoRepository.save(pago));
//
//				return ResponseEntity.ok(pagoRes);
//			} else {
//
//				String mensaje = MessageFormat.format("No se encontro el registro de {0} con {1} : {2} !", "pago", "id",
//						id);
//
//				if (logger.isDebugEnabled())
//					logger.error(" EmptyResultDataAccessException : " + mensaje);
//
//				ResponseJson responseJson = new ResponseJson();
//
//				responseJson.setCodigoError("PG0005");
//				responseJson.setMensajeError(mensaje);
//				responseJson.setResultado("ERROR");
//
//				// ErrorMessage errorMessage = new ErrorMessage("No se pudo encontrar el pago
//				// con ID " + id);
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);
//
//			}
//
//		} catch (DataAccessException e) {
//
//			if (logger.isDebugEnabled())
//				logger.error(" DataAccessException : (" + Thread.currentThread().getStackTrace()[1].getMethodName()
//						+ ") : " + e.getMessage());
//
//			String mensaje = MessageFormat.format("Error : {0} - detalle [ {1} : {2} ]",
//					"Error de lectura en la base de datos", e.getMessage(), e.getMostSpecificCause().getMessage());
//
//			ResponseJson responseJson = new ResponseJson();
//
//			responseJson.setCodigoError("PG0002");
//			responseJson.setMensajeError(mensaje);
//			responseJson.setResultado("ERROR");
//
//			// return ResponseEntity.ok(responseJson);
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
//
//		} catch (Exception e) {
//
//			if (logger.isDebugEnabled())
//				logger.error(" Exception : (" + Thread.currentThread().getStackTrace()[1].getMethodName() + ") : "
//						+ e.getMessage());
//
//			throw ServiceException.internalError("Error : {0} ", e.getMessage());
//
//		}
//
////		if(id != null) {
////			
////			for (Pago pago : pagos) {
////				if(pago.getId() == id) {
////					pago.setEstadoId(3);
////					return ResponseEntity.status(HttpStatus.OK).body(pago);
////				}
////			}
////			
////			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el pago");
////			
////		}
//
//		// return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en
//		// el cuerpo de la solicitud");
//
//	}

//	@GetMapping("/listar")
//	public ResponseEntity<?> listarPagos(){
//		
//		List<Pago> list = new ArrayList<Pago>(0);
//		
//		List<PagoEntity> listEntities = pagoRepository.findAll();
//		listEntities.forEach(entity -> list.add(PagoMapper.map(entity)));
//		
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//		
//	}

//	@PutMapping
//	public ResponseEntity<?> editarPago (@RequestBody Pago pago){
//		
//		if(pago != null) {
//			
//			for (Pago pagoList : pagos) {
//				if(pagoList.getId() == pago.getId()) {
//					return ResponseEntity.status(HttpStatus.OK).body(pago);
//				}
//			}
//			
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el pago");
//			
//		}
//		
//		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud");
//		
//	}
//	
//	@PatchMapping("/{id}")
//	public ResponseEntity<?> retornarPagoId (@PathVariable("id") Integer id){
//		
//		if(id != null) {
//			
//			for (Pago pago : pagos) {
//				if(pago.getId() == id) {
//					pago.setEstadoId(3);
//					return ResponseEntity.status(HttpStatus.OK).body(pago);
//				}
//			}
//			
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el pago");
//			
//		}
//		
//		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error en el cuerpo de la solicitud");	
//		
//	}

//	@PostMapping("/consultar-id/{id}")
//	public ResponseEntity<GstCategoria> findGstCategoria(@PathVariable("id") Long gstCategoriaId, @RequestBody GstCategoria gstCategoria, @RequestHeader Map<String, String> headers) {

//	@GetMapping
//    public ResponseEntity<String> unauthorized() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//    }

	private boolean validarMetodoDePago(Integer metodoPagoId) {

		Optional<MetodoPago> res = null;

		try {
			res = metodoPagoService.findById(metodoPagoId);
		} catch (Exception e) {
			throw e;
		}

		if (res != null && res.isPresent()) {
			return true;
		}

		return false;

//		if(metodoPagoService.findById(metodoPagoId) != null) {
//			return true;
//		}
//		
//		return false;

//		boolean valido = false;
//		
//		//Se valida el metodo de pago
//		if(metodoPagoId == 1 || metodoPagoId == 2 || metodoPagoId == 3) {
//			valido = true;
//		}
//		
//		return valido;

		// metodoPagoRepository.findById(metodoPagoId).orElseThrow(() -> new
		// EntityNotFoundException("Método de pago no encontrado")));
	}

	private boolean validarRefCompra(String refCompra) {

		// Se valida que el primer caracter sea una J y que su tamaño se igual a 13
		// caracteres
		return (refCompra.charAt(0) == 'J' && refCompra.length() == 13);
	}

//	private String voucherGenerator() {
//		int length = 10; // Longitud del código de voucher
//		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Caracteres permitidos
//																								// para el código de
//																								// voucher
//		Random random = new Random();
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < length; i++) {
//			int index = random.nextInt(characters.length());
//			char randomChar = characters.charAt(index);
//			sb.append(randomChar);
//		}
//		return sb.toString();
//
////	      String voucherCodigo = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
////	      
////	      return voucherCodigo;
//	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> retornarPagoId2(@PathVariable("id") Integer id) {

		if (logger.isDebugEnabled())
			logger.debug("ENTRA" + Thread.currentThread().getStackTrace()[1].getMethodName() + " /{id} : "
					+ (id != null ? id.toString() : ""));

		Pago response = new Pago();

		if (id == null) {
			String mensaje = MessageFormat.format("Error : {0} ", "No hay datos para realizar la operación");

			if (logger.isDebugEnabled())
				logger.error(mensaje);

			ResponseJson responseJson = new ResponseJson();

			responseJson.setMensajeError(mensaje);
			responseJson.setCodigoError("PG0001");
			responseJson.setResultado("ERROR");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no proporcionado en la petición");

		}

		Optional<Pago> res = null;

		try {
			res = pagoService.findById(id);

			if (res != null && res.isPresent()) {
				
				Pago pago = res.get();
				pago.setEstadoId(3);

				response = pagoService.save(pago);
				response.setMensaje(MessageFormat.format("El {0} ha sido retornado con exito", "Pago"));
				response.setResultado("OK");

				return ResponseEntity.ok(response);
			} else {

				String mensaje = MessageFormat.format("No se encontro el registro de {0} con {1} : {2} !", "pago", "id",
						id);

				if (logger.isDebugEnabled())
					logger.error(" EmptyResultDataAccessException : " + mensaje);

				ResponseJson responseJson = new ResponseJson();

				responseJson.setCodigoError("PG0005");
				responseJson.setMensajeError(mensaje);
				responseJson.setResultado("ERROR");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseJson);

			}

		} catch (DataAccessException e) {

			if (logger.isDebugEnabled())
				logger.error(" DataAccessException : (" + Thread.currentThread().getStackTrace()[1].getMethodName()
						+ ") : " + e.getMessage());

			String mensaje = MessageFormat.format("Error : {0} - detalle [ {1} : {2} ]",
					"Error de lectura en la base de datos", e.getMessage(), e.getMostSpecificCause().getMessage());

			ResponseJson responseJson = new ResponseJson();

			responseJson.setCodigoError("PG0002");
			responseJson.setMensajeError(mensaje);
			responseJson.setResultado("ERROR");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);

		} catch (Exception e) {

			if (logger.isDebugEnabled())
				logger.error(" Exception : (" + Thread.currentThread().getStackTrace()[1].getMethodName() + ") : "
						+ e.getMessage());

			throw ServiceException.internalError("Error : {0} ", e.getMessage());

		}

	}


}
