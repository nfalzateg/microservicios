package com.co.uniquindio.microservicios.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.uniquindio.microservicios.entities.MetodoPagoEntity;
import com.co.uniquindio.microservicios.mapper.MetodoPagoMapper;
import com.co.uniquindio.microservicios.models.MetodoPago;
import com.co.uniquindio.microservicios.repository.MetodoPagoRepository;

//import jakarta.persistence.EntityNotFoundException;

@Service
public class MetodoPagoService {
	
	private final MetodoPagoRepository metodoPagoRepository;
	
	@Autowired
	public MetodoPagoService (final MetodoPagoRepository metodoPagoRepository) {
		this.metodoPagoRepository = metodoPagoRepository;
	}
	
	@Transactional(readOnly=true)
	public Optional<MetodoPago> findById(Integer metodoPagoId) {
		try {

			Optional<MetodoPagoEntity> entity = metodoPagoRepository.findById(metodoPagoId);
			//MetodoPagoEntity entity = metodoPagoRepository.findById(metodoPagoId).orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado"));
			
			return entity.map(MetodoPagoMapper::map);
			
			//return MetodoPagoMapper.map(entity);
//		} catch (EntityNotFoundException ex) {
//			MetodoPago metodoPago = new MetodoPago();
//			metodoPago.setMensajeError(ex.getMessage());
//			metodoPago.setCodigoError("PG0002");
//			metodoPago.setResultado("ERROR");
//			return metodoPago;
		}
		catch (Exception e) {
			throw e;
		}
	}

}
