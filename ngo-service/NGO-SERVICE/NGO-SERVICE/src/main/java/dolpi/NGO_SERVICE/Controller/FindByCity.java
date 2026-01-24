package dolpi.NGO_SERVICE.Controller;

import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//fetch this data is microservice
@RestController
@RequestMapping("/find")
public class FindByCity {

    @Autowired
    private NgoRepository ngoRepository;

    @GetMapping("/getCity")
    public List<RegisterNgo> findngocity(@RequestParam String city){
        return ngoRepository.findByCity(city);
    }
}
