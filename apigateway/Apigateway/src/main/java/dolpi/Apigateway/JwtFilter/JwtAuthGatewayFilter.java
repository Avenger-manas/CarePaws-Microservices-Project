package dolpi.Apigateway.JwtFilter;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthGatewayFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        // Debug log to check if filter is triggered
        System.out.println("Gateway Filter triggered for: " + request.getURI());

        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header missing or invalid");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            // JWT validation & parse claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            // Fix: roles claim is a list in JWT, take first element
            String role = null;
            Object rolesClaim = claims.get("roles");
            if (rolesClaim instanceof List<?> list && !list.isEmpty()) {
                role = list.get(0).toString();
            }

            // Forward headers to downstream microservices
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-USERNAME", username)
                    .header("X-ROLE", role)
                    .header("X-GATEWAY", "true")
                    .build();

            return chain.filter(exchange.mutate()
                    .request(modifiedRequest)
                    .build());

        } catch (JwtException e) {
            System.out.println("JWT validation failed: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // High priority filter
    }
}