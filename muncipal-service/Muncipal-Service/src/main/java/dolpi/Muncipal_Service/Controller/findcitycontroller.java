package dolpi.Muncipal_Service.Controller;

import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/find")
@RestController
public class findcitycontroller {
    @Autowired
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @GetMapping("/getCity")
    public List<RegisterEntity> findngocity(@RequestParam String city){
        return muncipalCoprationRepo.findByCity(city);
    }
}
