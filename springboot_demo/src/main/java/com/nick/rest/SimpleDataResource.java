package com.nick.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/api/apm/masterData/entity")
public class SimpleDataResource {

	private static final Logger log = LoggerFactory.getLogger(SimpleDataResource.class);

	@RequestMapping(value = "/{entityName}", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getEntity(@PathVariable("entityName") String entityName, 
			@RequestParam(value = "fields", required = false) String fields,
			@RequestParam(value = "where", required = false) String whereClause,
			@RequestParam(value ="orderBy", required = false) String orderBy,
			@RequestParam(value = "page", required = false, defaultValue = "0") String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize) {
		log.debug("Enter getEntity.");
		log.debug("entityName: " + entityName + ", fields: " + fields + ", where: " + whereClause + ", orderBy: " + orderBy + ", page: " + page + ", pageSize: " + pageSize);
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleDataResource.class, args);
	}

}
