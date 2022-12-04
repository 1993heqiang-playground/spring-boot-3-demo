package com.example.springboot3demo.controller;

import com.example.springboot3demo.entity.Greeting;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GreetingHttpController {
	private final ObservationRegistry registry;

	public GreetingHttpController(ObservationRegistry registry) {
		this.registry = registry;
	}

	@GetMapping("/greeting/{name}")
	public Greeting greet(@PathVariable("name") String name, HttpServletRequest request) {
		boolean good = StringUtils.hasText(name) && Character.isUpperCase(name.charAt(0));
		if (!good) {
			throw new IllegalArgumentException("name is invalid.");
		}
		Observation observation = Observation.createNotStarted("greeting.name", this.registry);
		observation.lowCardinalityKeyValue("test", "greeting");
		return observation.observe(() -> new Greeting("Greeting " + name + "!"));
	}
}
