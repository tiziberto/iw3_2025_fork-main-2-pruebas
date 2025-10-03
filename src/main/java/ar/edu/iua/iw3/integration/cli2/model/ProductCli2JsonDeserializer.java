package ar.edu.iua.iw3.integration.cli2.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ar.edu.iua.iw3.model.business.BusinessException;
import ar.edu.iua.iw3.model.business.ICategoryBusiness;
import ar.edu.iua.iw3.model.business.NotFoundException;
import ar.edu.iua.iw3.util.JsonUtiles;

public class ProductCli2JsonDeserializer extends StdDeserializer<ProductCli2> {


	protected ProductCli2JsonDeserializer(Class<?> vc) {
		super(vc);
	}

	private ICategoryBusiness categoryBusiness;

	public ProductCli2JsonDeserializer(Class<?> vc, ICategoryBusiness categoryBusiness) {
		super(vc);
		this.categoryBusiness = categoryBusiness;
	}

	@Override
	public ProductCli2 deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
		ProductCli2 r = new ProductCli2();
		JsonNode node = jp.getCodec().readTree(jp);
 		
		//Añadimos la fecha de vencimiento:
 		String expirationStr = JsonUtiles.getString(node, "expiration_date,expiry,expDate,fecha_expiracion".split(","),null);
		Date expirationDate = null;
        if (expirationStr != null) {
            try {
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationStr);
            } catch (ParseException e) {
                throw new IOException("Formato de fecha inválido para expirationDate, se esperaba yyyy-MM-dd");
            }
        } else {
            throw new IOException("La fecha de expiración es obligatoria");
        }

		String productDesc = JsonUtiles.getString(node,
				"product,description,product_description,product_name".split(","), null);
		
		if (productDesc == null || productDesc.trim().isEmpty()) {
		    throw new IOException("El nombre del producto es obligatorio");
		}
		
		
		double price = JsonUtiles.getDouble(node, "product_price,price_product,price".split(","), 0);
		boolean stock = JsonUtiles.getBoolean(node, "stock,in_stock".split(","), false);
		r.setExpirationDate(expirationDate);
		r.setProduct(productDesc);
		r.setPrecio(price);
		r.setStock(stock);
		String categoryName = JsonUtiles.getString(node, "category,product_category,category_product".split(","), null);
		if (categoryName != null) {
			try {
				r.setCategory(categoryBusiness.load(categoryName));
			} catch (NotFoundException | BusinessException e) {
			}
		}
		return r;
	}

}
