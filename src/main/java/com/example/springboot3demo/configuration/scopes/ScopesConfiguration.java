package com.example.springboot3demo.configuration.scopes;

import java.util.UUID;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

/**
 * ScopesConfiguration
 *
 * @author HeQiang
 * @since 2022/11/26
 **/
@Configuration
public class ScopesConfiguration {
	ApplicationListener<ApplicationReadyEvent> scopeListener() {
		return new ApplicationListener<ApplicationReadyEvent>() {
			@Override
			public void onApplicationEvent(ApplicationReadyEvent event) {

			}
		};
	}
}

@Controller
@ResponseBody
class ContextController {
	final RequestContext requestContext;

	@Lazy
	ContextController(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	@GetMapping("/scopes/context")
	String requestScope() {
		return requestContext.getUuid();
	}
}

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestContext {
	private final String uuid = UUID.randomUUID().toString();

	String getUuid() {
		return uuid;
	}
}
