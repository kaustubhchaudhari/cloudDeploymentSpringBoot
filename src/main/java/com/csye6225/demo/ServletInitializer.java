/**
 * <KAUSTUBH_CHAUDHARI>, <001218494>, <chaudhari.k@husky.neu.edu>
 * <AKSHAY_NAKHAWA>, <001665873>, <nakhawa.a@husky.neu.edu>
 * <PRACHI_SAXENA>, <001220709>, <saxena.pr@husky.neu.edu>
 * <AKHILA_KUNCHE>, <001251306>, <kunche.a@husky.neu.edu>

 **/

package com.csye6225.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

}
