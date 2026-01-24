package dolpi.Muncipal_Service.Controller;

import dolpi.Muncipal_Service.DTO.RegisterDTO;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import dolpi.Muncipal_Service.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/muncipal-register")
public class RegisterController {

    @Autowired
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerngo(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles, @RequestHeader(value = "X-GATEWAY", required = true) String gateway, @RequestBody RegisterDTO registerDTO,@RequestParam String id){
        if (!roles.equals("ROLE_MUNCIPAL")) {
            throw new ResourcesNotFound("Not Found");
        }

        if (muncipalCoprationRepo.existsByUsername(username)) {
            throw new ResourcesNotFound("This Ngo Is Already Register");
        }

        return new ResponseEntity<>(registerService.registermuncipal(username,registerDTO,id), HttpStatus.OK);


    }
}
