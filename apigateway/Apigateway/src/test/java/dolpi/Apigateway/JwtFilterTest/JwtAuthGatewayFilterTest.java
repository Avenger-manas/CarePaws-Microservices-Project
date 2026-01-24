package dolpi.Apigateway.JwtFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JwtAuthGatewayFilterTest {
    @InjectMocks
    private JwtAuthGatewayFilter filter;

    @Mock
    private GatewayFilterChain chain;

    private static final String SECRET =
            "AJD73hd92JSHD73js822HHD9283HDHSJs992k1234567890";

    @Test
    void shouldAllowRequestForValidToken() throws Exception {

        JwtAuthGatewayFilter filter = new JwtAuthGatewayFilter();

        String SECRET =
                "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_32_CHARS_MINIMUM";

        // inject secret
        Field field = JwtAuthGatewayFilter.class.getDeclaredField("secret");
        field.setAccessible(true);
        field.set(filter, SECRET);

        // create valid JWT
        String token = Jwts.builder()
                .setSubject("manas")
                .claim("roles", List.of("ROLE_USER"))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256)
                .compact();

        MockServerHttpRequest request =
                MockServerHttpRequest.get("/test")
                        .header("Authorization", "Bearer " + token)
                        .build();

        MockServerWebExchange exchange =
                MockServerWebExchange.from(request);

        when(chain.filter(any())).thenReturn(Mono.empty());

        filter.filter(exchange, chain).block();

        assertNull(exchange.getResponse().getStatusCode());
    }

}
