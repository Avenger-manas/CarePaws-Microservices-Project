package dolpi.Muncipal_Service.ServiceTest;



import dolpi.Muncipal_Service.DTO.RegisterDTO;
import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import dolpi.Muncipal_Service.Service.RegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    @Mock
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void registermuncipal_success() {
        // -------- Arrange --------
        String username = "admin";
        String id = "123";

        RegisterDTO dto = new RegisterDTO();
        dto.setMunicipalName("Bareilly Nagar Nigam");
        dto.setCity("Bareilly");
        dto.setHelplineNumber("1800-123");
        dto.setOfficeAddress("Civil Lines");
        dto.setEmail("test@gmail.com");
        dto.setRegistrationCode("REG123");
        dto.setState("UP");
        dto.setDescription("Municipal Corporation");

        when(muncipalCoprationRepo.save(any(RegisterEntity.class)))
                .thenReturn(new RegisterEntity());

        // -------- Act --------
        ResponseEntity<?> response =
                registerService.registermuncipal(username, dto, id);

        // -------- Assert --------
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Sucsessfully Register", response.getBody());

        verify(muncipalCoprationRepo, times(1))
                .save(any(RegisterEntity.class));
    }

    }

