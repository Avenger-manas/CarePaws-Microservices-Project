package dolpi.CarePaws.ControllerTest;

import dolpi.CarePaws.Controller.GoogleAuthController;
import dolpi.CarePaws.Service.GoogleAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GoogleAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GoogleAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoogleAuthService googleAuthService;

    @Test
    void testGoogleAuthCallback_success() throws Exception {

        // given
        String code = "test_code";
        String check = "test_check";
        String jwtToken = "dummy_jwt_token";

        when(googleAuthService.handleGoogleCallback(code, check))
                .thenReturn(jwtToken);

        // when + then
        mockMvc.perform(get("/auth/google/callback")
                        .param("code", code)
                        .param("check", check))
                .andExpect(status().isOk())
                .andExpect(content().string(jwtToken));
    }
}
