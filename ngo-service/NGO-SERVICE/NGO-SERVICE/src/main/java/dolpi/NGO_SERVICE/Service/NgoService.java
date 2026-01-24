package dolpi.NGO_SERVICE.Service;

import dolpi.NGO_SERVICE.Dto.RegisterNgodto;
import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NgoService {

    @Autowired
    private NgoRepository ngoRepository;

    public String registerngo(String username, RegisterNgodto registerNgodto,String id){
        RegisterNgo registerNgo=new RegisterNgo();

        registerNgo.setId(id);
        registerNgo.setUsername(username);
        registerNgo.setNgoname(registerNgodto.getNgoname());
        registerNgo.setEmail(registerNgodto.getEmail());
        registerNgo.setAddress(registerNgodto.getAddress());
        registerNgo.setCity(registerNgodto.getCity());
        registerNgo.setAnimaltype(registerNgodto.getAnimaltype());
        registerNgo.setPhonenumber(registerNgodto.getPhonenumber());
        registerNgo.setDescription(registerNgodto.getDescription());

        ngoRepository.save(registerNgo);
        return "Ngo Sucessfully Register";

    }
}
