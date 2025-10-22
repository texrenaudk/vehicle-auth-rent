//package com.renaudk.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//
//@Component
//public class LoggingFilter implements GlobalFilter {
//
//    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        long startTime = System.currentTimeMillis();
//
//        logger.info("Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
//
//        return chain.filter(exchange)
//                .doOnSuccessOrError((aVoid, throwable) -> {
//                    long duration = System.currentTimeMillis() - startTime;
//                    logger.info("Response: {} - Status: {} - Duration: {}ms",
//                            exchange.getRequest().getURI(),
//                            exchange.getResponse().getStatusCode(),
//                            duration);
//                });
//    }
//}
