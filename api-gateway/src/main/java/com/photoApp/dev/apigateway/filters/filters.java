package com.photoApp.dev.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class filters
{
    final Logger logger = LoggerFactory.getLogger(filters.class);


    @Order(1)
    @Bean
    public GlobalFilter secondFilter() {
        return ((exchange, chain) -> {
            logger.info("Executing Second prefilter");

            return chain.filter(exchange).then(Mono.fromRunnable(() ->
            {
                logger.info("Executing Second postFilter");
            }));
        });
    }

    @Order(2)
    @Bean
        public GlobalFilter thirdFilter ()
        {
            return ((exchange, chain) -> {
                logger.info("Executing third prefilter");

                return chain.filter(exchange).then(Mono.fromRunnable(() ->
                {
                    logger.info("Executing third postFilter");
                }));
            });
        }


        }

