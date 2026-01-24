package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.NgoDto;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NgoService {

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //create new ngo
    public String craetenewngo(NgoDto ngoDto){
        NgoEntity ngoEntity=new NgoEntity();

        String encodepassword=passwordEncoder.encode(ngoDto.getPassword());


        ngoEntity.setUsername(ngoDto.getUsername());
        ngoEntity.setPassword(encodepassword);
        ngoEntity.setEmail(ngoDto.getEmail());
        ngoEntity.setRoles(Collections.singletonList("NGO"));

        ngoRepository.save(ngoEntity);

        return "Suucefully Signup Ngo";


    }
}

