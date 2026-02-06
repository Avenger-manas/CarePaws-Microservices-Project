package dolpi.Report_Service.Interface;

import dolpi.Report_Service.Dto.RegisterEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="CAREPAWS-MUNCIPAL-SERVICE")
public interface MuncipalFeign {
    @GetMapping("/find/getCity")
    List<RegisterEntity> getmunciapl(@RequestParam String city);
}
