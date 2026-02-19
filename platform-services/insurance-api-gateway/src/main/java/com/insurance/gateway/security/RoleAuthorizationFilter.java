package com.insurance.gateway.security;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RoleAuthorizationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        return exchange.getPrincipal()
                .cast(Authentication.class)
                .flatMap(auth -> {

                    boolean isAdmin = auth.getAuthorities()
                            .stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (path.startsWith("/admin") && !isAdmin) {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }

                    return chain.filter(exchange);
                });
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
