package dolpi.CarePaws.JwtToken;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class JwtUtilTest {
    // Direct object creation (NO Mockito)
    private final JwtUtil jwtUtil = new JwtUtil();

    // Reusable UserDetails
    private UserDetails createUser() {
        return new User(
                "testuser",
                "password",
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")

                )
        );
    }

    // ------------------ TOKEN GENERATION ------------------

    @Test
    void generateToken_shouldCreateToken() {
        UserDetails userDetails = createUser();

        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    // ------------------ EXTRACT USERNAME ------------------

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        UserDetails userDetails = createUser();
        String token = jwtUtil.generateToken(userDetails);

        String username = jwtUtil.extractUsername(token);

        assertEquals("testuser", username);
    }

    // ------------------ EXTRACT ROLES ------------------

    @Test
    void extractRoles_shouldReturnRoles() {
        UserDetails userDetails = createUser();
        String token = jwtUtil.generateToken(userDetails);

        List<String> roles = jwtUtil.extractRoles(token);

        assertEquals(1, roles.size());
        assertTrue(roles.contains("ROLE_USER"));
    }

    // ------------------ TOKEN VALID ------------------

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        UserDetails userDetails = createUser();
        String token = jwtUtil.generateToken(userDetails);

        boolean valid = jwtUtil.isTokenValid(token, userDetails);

        assertTrue(valid);
    }

    @Test
    void isTokenValid_shouldReturnFalseForWrongUser() {
        UserDetails userDetails = createUser();
        String token = jwtUtil.generateToken(userDetails);

        UserDetails otherUser =
                new User("otheruser", "pass", List.of());

        boolean valid = jwtUtil.isTokenValid(token, otherUser);

        assertFalse(valid);
    }
}
