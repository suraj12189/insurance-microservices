package com.insurance.gateway.security;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserContextPropagationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        return exchange.getPrincipal()
                .cast(Authentication.class)
                .flatMap(auth -> {

                    var mutatedRequest =
                            exchange.getRequest().mutate()
                                    .header("X-User-Id", auth.getName())
                                    .header("X-User-Roles",
                                            auth.getAuthorities().toString())
                                    .build();

                    return chain.filter(
                            exchange.mutate()
                                    .request(mutatedRequest)
                                    .build());
                });
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
