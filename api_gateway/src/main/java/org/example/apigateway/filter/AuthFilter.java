package org.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.apigateway.auth.TokenValidator;
import org.example.apigateway.redis.service.RedisService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;




@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public static class Config{}
    private final RedisService redisService;
    private final TokenValidator tokenValidator;

    public AuthFilter(RedisService redisService, TokenValidator tokenValidator) {
        super(Config.class);
        this.redisService = redisService;
        this.tokenValidator = tokenValidator;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Request Header에 토큰이 존재하지 않을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("토큰이 존재하지 않습니다.");
                return handleUnAuth(exchange);
            }

            // Request Header에서 토큰 문자열 받아오기
            String accessToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7);

            if (!tokenValidator.validateToken(accessToken)) {
                log.warn("유효하지 않은 토큰입니다.");
                return handleUnAuth(exchange);
            };

//            String username = tokenValidator.getUsername(accessToken);
//
//
//            request = request.mutate()
//                    .header("username", username)
//                    .build();

            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());

        });


    }

    private Mono<Void> handleUnAuth(ServerWebExchange exchange) {
        log.warn("Handling Unauthorized request");

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

}
