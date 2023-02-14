package com.pe.gidtec.servicedesk.lib.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.gidtec.servicedesk.lib.annotation.HttpHeadersMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpHeaderArgumentResolver implements HandlerMethodArgumentResolver {

    private ObjectMapper mapper;

    public HttpHeaderArgumentResolver(ObjectMapper mapper) {
        super();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.mapper = mapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(HttpHeadersMapping.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        log.debug("Resolving annotation @HttpHeadersMapping..");

        Map<String, Object> headers = this.convertFromHeaders(exchange.getRequest().getHeaders(). toSingleValueMap());

        Object obj = mapper.convertValue(headers, (Class<?>) parameter.getParameterType());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        } else {
            return Mono.just(obj);
        }
    }

    private Map<String, Object> convertFromHeaders(Map<String, String> headers) {
        Map<String, Object> headersMap = new HashMap<>();
        headers.forEach(headersMap::put);
        return headersMap;
    }
}
