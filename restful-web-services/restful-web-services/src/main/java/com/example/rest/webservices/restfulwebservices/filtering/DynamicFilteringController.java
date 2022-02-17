package com.example.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/*
 * 
 *This class is used for dynamic filtering where in we return different set of attributes of
 *class SomeFilteringBean for different get mappings 
 */

@RestController
public class DynamicFilteringController {

	//field1, field2
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue retrieve() {
		SomeFilteringBean someFilteringBean = new SomeFilteringBean("value1", "value2", "value3", "value4");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeFilterBean", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(someFilteringBean);
		mapping.setFilters(filters);

		return mapping;

	}
	
	//field2, field3, field4
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue getList() {
		List<SomeFilteringBean> someList = Arrays.asList(new SomeFilteringBean("value1", "value2", "value3", "value4"),
				new SomeFilteringBean("value11", "value22", "value33", "value44"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3", "field4");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeFilterBean", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(someList);
		mapping.setFilters(filters);
		return mapping;
		
		
	}

}
