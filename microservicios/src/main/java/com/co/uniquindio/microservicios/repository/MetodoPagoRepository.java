package com.co.uniquindio.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.uniquindio.microservicios.entities.MetodoPagoEntity;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPagoEntity, Integer>{

}
