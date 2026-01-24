package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.RegisterNgo;
import dolpi.Report_Service.Interface.NGOFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class NgoFeignServiceTest {
    @InjectMocks
    private NgoFeignService ngoFeignService;

    @Mock
    private NGOFeign ngoFeign;

    @Test
    void findNGO_success() throws Exception {
        String city = "Delhi";

        RegisterNgo ngo1 = new RegisterNgo();
        ngo1.setNgoname("paws");
        ngo1.setCity("Bareilly");
        RegisterNgo ngo2 = new RegisterNgo();
        ngo2.setNgoname("pas");
        ngo2.setCity("Lal");


        List<RegisterNgo> mockList = List.of(ngo1, ngo2);

        when(ngoFeign.getNGO(city)).thenReturn(mockList);

        // when
        List<RegisterNgo> future =
                ngoFeignService.findNGO(city);


        // then
        assertNotNull(future);
        assertEquals(2, future.size());
        assertEquals("paws", future.get(0).getNgoname());
        assertEquals("pas", future.get(1).getNgoname());

        verify(ngoFeign, times(1)).getNGO(city);
    }

    @Test
    void ngoFallback_shouldReturnEmptyList() throws Exception {
        String city = "Mumbai";
        Throwable ex = new RuntimeException("Service Down");

        // when
        List<RegisterNgo> future =
                ngoFeignService.ngoFallback(city, ex);


        // then
        assertNotNull(future);
        assertTrue(future.isEmpty());
    }
}
