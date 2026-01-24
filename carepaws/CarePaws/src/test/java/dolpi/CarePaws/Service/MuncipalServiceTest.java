package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.MuncipalDTO;
import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Repository.MuncipalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MuncipalServiceTest {
    @InjectMocks
    private MuncipalService muncipalService;

    @Mock
    private MuncipalRepository muncipalRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateNewMuncipalSuccessfully() {

        // 1️ Arrange (input data)
        MuncipalDTO dto = new MuncipalDTO();
        dto.setUsername("muncipal1");
        dto.setPassword("1234");
        dto.setEmail("m@test.com");

        // 2️ Mock behaviour
        when(passwordEncoder.encode("1234"))
                .thenReturn("encoded1234");

        when(muncipalRepository.save(any(Muncipalcopration.class)))
                .thenReturn(new Muncipalcopration());

        // 3 Act (call method)
        String result = muncipalService.craetenewmuncipal(dto);

        // 4 Assert (verify output)
        assertEquals("Succesfully Created", result);

        // 5️ Verify interactions (MOST IMPORTANT)
        verify(passwordEncoder, times(1)).encode("1234");
        verify(muncipalRepository, times(1)).save(any(Muncipalcopration.class));
    }
}
