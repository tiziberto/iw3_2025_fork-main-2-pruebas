package ar.edu.iua.iw3.integration.cli2.model.persistence;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.iw3.integration.cli2.model.ProductCli2;
import ar.edu.iua.iw3.integration.cli2.model.ProductCli2SlimView;

@Repository
public interface ProductCli2Respository extends JpaRepository<ProductCli2, Long> {
	public List<ProductCli2> findByExpirationDateBeforeOrderByExpirationDateDesc(Date expirationDate);
	
	public List<ProductCli2SlimView> findByOrderByPrecioDesc();
	
	// Métodos para búsqueda por rango de precios
	public List<ProductCli2> findByPrecioBetweenOrderByPrecio(Double startPrice, Double endPrice);
	public List<ProductCli2> findByPrecioGreaterThanEqualOrderByPrecio(Double startPrice);
	public List<ProductCli2> findByPrecioLessThanEqualOrderByPrecio(Double endPrice);
	public List<ProductCli2> findAllByOrderByPrecio();
}

