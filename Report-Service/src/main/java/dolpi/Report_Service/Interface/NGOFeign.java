package dolpi.Report_Service.Interface;

import dolpi.Report_Service.Dto.RegisterNgo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="CAREPAWS-NGO-SERVICE",url = "http://localhost:8080")
public interface NGOFeign {
    @GetMapping("/find/getCity")
    List<RegisterNgo> getNGO(@RequestParam String city);
}
