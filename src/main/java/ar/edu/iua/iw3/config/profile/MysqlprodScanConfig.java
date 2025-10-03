package ar.edu.iua.iw3.config.profile;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
    "ar.edu.iua.iw3.model.persistence",
    "ar.edu.iua.iw3.auth.persistence",},excludeFilters = {
        //TRABAJO PRACTICO 3: comentamos las excepciones porque ahora vamos a usar los 2 perfiles en simultaneo 
    		//@ComponentScan.Filter(type = FilterType.REGEX, pattern = "ar\\.edu\\.iua\\.iw3\\.integration\\.cli1\\..*" ),
    		//@ComponentScan.Filter(type = FilterType.REGEX, pattern = "ar\\.edu\\.iua\\.iw3\\.integration\\.cli2\\..*" )
    })
@EntityScan(basePackages = { 
    "ar.edu.iua.iw3.model", 
    "ar.edu.iua.iw3.auth"
})
@Profile("mysqlprod")
public class MysqlprodScanConfig {
}