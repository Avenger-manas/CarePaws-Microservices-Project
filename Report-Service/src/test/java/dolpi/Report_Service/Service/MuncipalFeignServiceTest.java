package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.RegisterEntity;
import dolpi.Report_Service.Interface.MuncipalFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MuncipalFeignServiceTest {
    @InjectMocks
    private MuncipalFeignService muncipalFeignService;

    @Mock
    private MuncipalFeign muncipalFeign;

    @Test
    void findMunicipal_success() throws Exception {
        // given
        String city = "Delhi";

        RegisterEntity entity = new RegisterEntity();
        entity.setCity(city);

        List<RegisterEntity> mockList = List.of(entity);

        when(muncipalFeign.getmunciapl(city))
                .thenReturn(mockList);

        // when
        List<RegisterEntity> future =
                muncipalFeignService.findMunicipal(city);



        // then
        assertNotNull(future);
        assertEquals(1, future.size());
        assertEquals("Delhi", future.get(0).getCity());

        verify(muncipalFeign, times(1))
                .getmunciapl(city);
    }

    @Test
    void municipalFallback_shouldReturnEmptyList() throws Exception {
        // given
        String city = "Delhi";
        Throwable ex = new RuntimeException("Service Down");

        // when
        List<RegisterEntity> future =
                muncipalFeignService.municipalFallback(city, ex);

       // assertNotNull(result);
        assertTrue(future.isEmpty());
    }

}
