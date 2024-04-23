package com.airp.airp;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.APIKEY;

@SpringBootApplication
@SecurityScheme(name = "Authorization", scheme = "basic", type = APIKEY, in = HEADER)
public class AirpApplication extends SpringBootServletInitializer {
	public static final Logger log = LoggerFactory.getLogger(AirpApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(AirpApplication.class, args);
		log.info("""

				================================================================================================
				                                   AIRP started...
				================================================================================================"""
		);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AirpApplication.class);
	}

}
