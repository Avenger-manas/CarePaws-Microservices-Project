package dolpi.Muncipal_Service.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InternalServiceFilter extends OncePerRequestFilter {
    @Value("${internal.service.token}")
    private String internalToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        if (request.getRequestURI().startsWith("/find")) {

            String token = request.getHeader("X-INTERNAL-TOKEN");

            if (!internalToken.equals(token)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
