package dolpi.NGO_SERVICE.Controller;

import dolpi.NGO_SERVICE.Dto.RegisterNgodto;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import dolpi.NGO_SERVICE.Service.NgoService;
import dolpi.NGO_SERVICE.Service.ResucueNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/ngo")
@Slf4j
public class NgoRegister {


    //register ngo
    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private NgoService ngoService;

    @PostMapping("/register")
    public ResponseEntity<?> registerngo(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles,@RequestHeader(value = "X-GATEWAY", required = true) String gateway, @RequestBody RegisterNgodto registerNgodto,@RequestParam String id) {
        if (!roles.equals("ROLE_NGO")) {
            throw new ResourcesNotFound("Not Found");
        }

        if (ngoRepository.existsByUsername(username)) {
            throw new ResourcesNotFound("This Ngo Is Already Register");
        }

            return new ResponseEntity<>(ngoService.registerngo(username, registerNgodto,id), HttpStatus.CREATED);
        }

    //This Controller Is Notify NGO
    @Autowired
    private ResucueNotifyService resucueNotifyService;

    @GetMapping("/notify")
    public ResponseEntity<?> acceptrescue(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles, @RequestHeader(value = "X-GATEWAY", required = true) String gateway,@RequestParam String ngomnplId){
        if (!roles.equals("ROLE_NGO")) {
            throw new ResourcesNotFound("Not Found");
        }

        log.info("Notfy Controller Is RUN");
        return new ResponseEntity<>(resucueNotifyService.notifyservice(ngomnplId), HttpStatus.OK);

    }

}
