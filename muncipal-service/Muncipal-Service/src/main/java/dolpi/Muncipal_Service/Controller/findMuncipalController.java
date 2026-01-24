package dolpi.Muncipal_Service.Controller;

import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//this data fetch only microservice api
@RestController
@RequestMapping("/find")
public class findMuncipalController {

    @Autowired
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @GetMapping("/getcity")
    public List<RegisterEntity> getcitybasemuncipal(@RequestParam String city){
        return muncipalCoprationRepo.findByCity(city);
    }
}
