package ar.edu.iua.iw3.integration.cli2.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ComponentCli2JsonSerializer   extends StdSerializer<ComponentCli2>{

	protected ComponentCli2JsonSerializer(Class<ComponentCli2> t) {
		super(t);
	}

	@Override
	public void serialize(ComponentCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		/*
		 * {
		 * "id": 123,
		 * "component": "Harina"
		 * }
		 */
		gen.writeStartObject(); // {
		gen.writeNumberField("id",value.getId()); // "id": 123,
		gen.writeStringField("component", value.getComponent()); //"component": "Harina"
		gen.writeEndObject(); // }
		
	}



}
