package com.photoApp.dev.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class preFilter1 implements GlobalFilter {
    final Logger logger = LoggerFactory.getLogger(preFilter1.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("First filter triggered");

        String request = exchange.getRequest().getPath().toString();
        logger.info("Request path" + request);

      HttpHeaders headers = exchange.getRequest().getHeaders();
      Set<String> headerName  = headers.keySet();
      headerName.forEach((headerNames) -> {

          String header = headers.getFirst(headerNames);
          logger.info("Header Name " + header);
      });
      return chain.filter(exchange);

    }
}
