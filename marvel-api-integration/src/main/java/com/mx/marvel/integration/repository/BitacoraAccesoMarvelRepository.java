package com.mx.marvel.integration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MBL
 */

@Repository
public interface BitacoraAccesoMarvelRepository extends CrudRepository<BitacoraAccesoMarvelEntity, Long> {
}