package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.NgoDto;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Repository.NgoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NgoServiceTest {
    @Mock
    private NgoRepository ngoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private NgoService ngoService;

    @Test
    void createNgoTest() {

        // input DTO
        NgoDto dto = new NgoDto();
        dto.setUsername("ngo1");
        dto.setPassword("123");
        dto.setEmail("ngo@gmail.com");

        // mock password encoder
        when(passwordEncoder.encode("123"))
                .thenReturn("encoded123");

        // call method
        String result = ngoService.craetenewngo(dto);

        // verify save called
        verify(ngoRepository, times(1))
                .save(any(NgoEntity.class));

        // verify response
        assertEquals("Suucefully Signup Ngo", result);
    }
}
