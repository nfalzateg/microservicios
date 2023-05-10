package com.co.uniquindio.microservicios.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.uniquindio.microservicios.entities.PagoEntity;
import com.co.uniquindio.microservicios.mapper.PagoMapper;
import com.co.uniquindio.microservicios.models.Pago;
import com.co.uniquindio.microservicios.repository.PagoRepository;

@Service
public class PagoService {
	
	private final PagoRepository pagoRepository;
	
	@Autowired
	public PagoService (final PagoRepository pagoRepository) {
		this.pagoRepository = pagoRepository;
	}
	
	@Transactional
	public Pago save(Pago pago) {
		try {
			
			if(pago.getEstadoId() == null) {
				pago.setEstadoId(2);
			}
			if(pago.getVoucherId() == null) {
				pago.setVoucherId(voucherGenerator());
			}
			
			pago.setFecha(new Timestamp((new java.util.Date()).getTime()));
			
			return PagoMapper.map(pagoRepository.save(PagoMapper.map(pago)));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true)
	public Optional<Pago> findById(Integer id) {
		try {

			Optional<PagoEntity> entity = pagoRepository.findById(id);
			
			return entity.map(PagoMapper::map);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true)
	public List<Pago> findAll() {
		try {
			List<Pago> list = new ArrayList<Pago>(0);
			
			List<PagoEntity> listEntities = pagoRepository.findAll();
			listEntities.forEach(entity -> list.add(PagoMapper.map(entity)));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private String voucherGenerator() {
		int length = 10; // Longitud del código de voucher
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Caracteres permitidos
																								// para el código de
																								// voucher
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			char randomChar = characters.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

}
