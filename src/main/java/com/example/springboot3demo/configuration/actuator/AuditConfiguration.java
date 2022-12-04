package com.example.springboot3demo.configuration.actuator;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ActuatorConfiguration
 *
 * @author HeQiang
 * @since 2022/11/27
 **/
@Configuration
public class AuditConfiguration {

	@Bean
	ApplicationListener<ApplicationReadyEvent> auditApplicationListener() {
		return event -> {
			ConfigurableApplicationContext context = event.getApplicationContext();
			AuditApplicationEvent auditApplicationEvent =
					new AuditApplicationEvent("User", "Test");
			context.publishEvent(auditApplicationEvent);
		};
	}

	@Bean
	AuditEventRepository auditEventRepository() {
		return new InMemoryAuditEventRepository();
	}

}

@RestController
class AuditController {

	private final AuditEventRepository auditEventRepository;

	public AuditController(AuditEventRepository auditEventRepository) {
		this.auditEventRepository = auditEventRepository;
	}

	@GetMapping("/audit/{principal}/{type}")
	List<AuditEvent> findAuditEvent(@PathVariable("principal") String principal,
			@PathVariable("type") String type) {
		Instant oneHourBefore = Instant.now().minus(Duration.ofHours(1L));
		return auditEventRepository.find(principal, oneHourBefore, type);
	}

	@PostMapping("/audit")
	void addAuditEvent(@RequestBody Map<String, String> params) {
		String principal = params.get("principal");
		String type = params.get("type");
		AuditEvent event = new AuditEvent(principal, type);
		auditEventRepository.add(event);
	}

}