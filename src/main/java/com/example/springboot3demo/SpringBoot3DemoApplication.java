package com.example.springboot3demo;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringBoot3DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3DemoApplication.class, args);
	}

}

@RestController
class GreetingHttpController {
	private final ObservationRegistry registry;

	public GreetingHttpController(ObservationRegistry registry) {
		this.registry = registry;
	}

	@GetMapping("/greeting/{name}")
	Greeting greet(@PathVariable("name") String name) {
		boolean good = StringUtils.hasText(name) && Character.isUpperCase(name.charAt(0));
		if (!good) {
			throw new IllegalArgumentException("name is invalid.");
		}
		return Observation.createNotStarted("greeting.name", this.registry)
				.observe(() -> new Greeting("Greeting " + name + "!"));
	}
}

@ControllerAdvice
class ProblemDetailErrorHandlingControllerAdvice {
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail onException() {
//		request.getAttributeNames().asIterator().forEachRemaining(attributeName ->
//				System.out.println("AttributeName: " + attributeName));
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Params is error.");
	}
}

record Greeting(String message) {
}
