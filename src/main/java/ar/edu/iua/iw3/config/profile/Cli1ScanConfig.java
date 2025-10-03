package ar.edu.iua.iw3.config.profile;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ar.edu.iua.iw3.integration.cli1.model.persistence",
excludeFilters = {
	//TRABAJO PRACTICO 3: comentamos la excepcion porque ahora vamos a usar los 2 perfiles en simultaneo 
		//@ComponentScan.Filter(type = FilterType.REGEX, pattern = "ar\\.edu\\.iua\\.iw3\\.integration\\.cli2\\..*" )
		//Se pueden definir más filtros de exclusión
		//,@ComponentScan.Filter(type = FilterType.REGEX, pattern = "org\\.magm\\.backend\\.integration\\.cliN\\..*" )
		
})
@EntityScan(basePackages = { 
		//"ar.edu.iua.iw3.model", 
		//"ar.edu.iua.iw3.auth", 
		"ar.edu.iua.iw3.integration.cli1.model" 
},
basePackageClasses = {
		// Se pueden cargar entidades particulares que no estén en los paquetes base
		//ar.edu.iua.iw3.integration.cliN.model.Entidad1.class, 
		//ar.edu.iua.iw3.integration.cliN.model.Entidad2.class
})


//@ConditionalOnExpression(value = "'${spring.profiles.active:-}'=='cli1'")
@Profile("cli1")
public class Cli1ScanConfig {
}