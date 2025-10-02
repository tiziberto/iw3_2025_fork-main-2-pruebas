package ar.edu.iua.iw3.integration.cli2.model.business;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.iua.iw3.integration.cli2.model.ProductCli2;
import ar.edu.iua.iw3.integration.cli2.model.ProductCli2JsonDeserializer;
import ar.edu.iua.iw3.integration.cli2.model.ProductCli2SlimView;
import ar.edu.iua.iw3.integration.cli2.model.persistence.ProductCli2Respository;
import ar.edu.iua.iw3.model.business.BusinessException;
import ar.edu.iua.iw3.model.business.FoundException;
import ar.edu.iua.iw3.model.business.ICategoryBusiness;
import ar.edu.iua.iw3.util.JsonUtiles;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductCli2Business implements IProductCli2Business {

	@Autowired(required = false)
	private ProductCli2Respository productDAO;

	@Override
	public List<ProductCli2> listExpired(Date date) throws BusinessException {
		try {
			return productDAO.findByExpirationDateBeforeOrderByExpirationDateDesc(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public List<ProductCli2SlimView> listSlim() throws BusinessException {
		try {
			return productDAO.findByOrderByPrecioDesc();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}
	
	@Autowired(required = false)
	private ICategoryBusiness categoryBusiness;

	@Override
    public ProductCli2 addExternal(String json) throws FoundException, BusinessException {
        try {
            
            ObjectMapper mapper = JsonUtiles.getObjectMapper(
                    ProductCli2.class,
                    new ProductCli2JsonDeserializer(ProductCli2.class, categoryBusiness),
                    null
            );

            ProductCli2 product = mapper.readValue(json, ProductCli2.class);

         
            if (product.getProduct() == null || product.getProduct().trim().isEmpty()) {
                throw new BusinessException("El nombre del producto es obligatorio");
            }

            if (product.getExpirationDate() == null) {
                throw new BusinessException("La fecha de expiración es obligatoria");
            }

            return productDAO.save(product);

        } catch (BusinessException e) {
            throw e;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error("Error de formato JSON en addExternal cli2", e);
            throw new BusinessException("Formato JSON inválido: " + e.getMessage());
        } catch (java.io.IOException e) {
            log.error("Error de formato en addExternal cli2", e);
            throw new BusinessException("Error de formato: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error general en addExternal cli2", e);
            throw BusinessException.builder().ex(e).build();
        }
    }

    @Override
    public List<ProductCli2> listByPriceRange(Double startPrice, Double endPrice) throws BusinessException {
        try {
            // Caso 1: Ambos parámetros presentes
            if (startPrice != null && endPrice != null) {
                return productDAO.findByPrecioBetweenOrderByPrecio(startPrice, endPrice);
            }
            // Caso 2: Solo startPrice (precio mínimo)
            else if (startPrice != null) {
                return productDAO.findByPrecioGreaterThanEqualOrderByPrecio(startPrice);
            }
            // Caso 3: Solo endPrice (precio máximo)
            else if (endPrice != null) {
                return productDAO.findByPrecioLessThanEqualOrderByPrecio(endPrice);
            }
            // Caso 4: Ningún parámetro - todos los productos
            else {
                return productDAO.findAllByOrderByPrecio();
            }
        } catch (Exception e) {
            log.error("Error en listByPriceRange cli2", e);
            throw BusinessException.builder().ex(e).build();
        }
    }
	
}

