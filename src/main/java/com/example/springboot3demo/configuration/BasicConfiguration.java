package com.example.springboot3demo.configuration;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

/**
 * BasicConfiguration
 *
 * @author HeQiang
 * @since 2022/11/25
 **/
@Configuration
public class BasicConfiguration {

	@Bean
	ApplicationListener<ApplicationReadyEvent> basicApplicationListener(CustomRepository customRepository) {
		return new ApplicationListener<ApplicationReadyEvent>() {
			@Override
			public void onApplicationEvent(ApplicationReadyEvent event) {
				customRepository.saveAll(
						Stream.of("A", "B", "C").map(name->new Customer(null, name))
								.toList()
				).forEach(System.out::println);
			}
		};
	}

}

record Customer(@Id	 Long id, String name) {}

interface CustomRepository extends CrudRepository<Customer,Long> {

}