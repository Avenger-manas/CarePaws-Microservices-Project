package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.UserDTO;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserTest() {

        // given (input)
        UserDTO dto = new UserDTO();
        dto.setUsername("user1");
        dto.setPassword("1234");
        dto.setEmail("user@gmail.com");

        // mock password encoder
        when(passwordEncoder.encode("1234"))
                .thenReturn("encoded1234");

        // when (method call)
        String result = userService.craetenewuser(dto);

        // then (verify)
        verify(userRepository, times(1))
                .save(any(UserEntity.class));

        assertEquals("User is Succesfully Register", result);
    }
}
