package dolpi.NGO_SERVICE.ServicesTest;

import dolpi.NGO_SERVICE.Dto.RegisterNgodto;
import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import dolpi.NGO_SERVICE.Service.NgoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NgoServiceTest {
    @Mock
    private NgoRepository ngoRepository;

    @InjectMocks
    private NgoService ngoService;

    @Test
    void Register_Success(){
        RegisterNgodto dto=new RegisterNgodto();
        dto.setNgoname("CarePaws");
        dto.setEmail("carepaws@test.com");
        dto.setCity("Bareilly");
        dto.setAddress("123 Street");
        dto.setAnimaltype("Dog");
        dto.setPhonenumber("9876543210");
        dto.setDescription("Helping animals");

        String result=ngoService.registerngo("testuser",dto,"123");

        // Assert
        assertEquals("Ngo Sucessfully Register", result);

        verify(ngoRepository, times(1))
                .save(any(RegisterNgo.class));
    }
}
