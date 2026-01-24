package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.MuncipalDTO;
import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Repository.MuncipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MuncipalService {
    @Autowired
    private MuncipalRepository muncipalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //create new muncipal acount
    public String craetenewmuncipal(MuncipalDTO muncipalDTO){
        Muncipalcopration muncipalcopration=new Muncipalcopration();

        String encodepassword=passwordEncoder.encode(muncipalDTO.getPassword());
        muncipalcopration.setUsername(muncipalDTO.getUsername());
        muncipalcopration.setPassword(encodepassword);
        muncipalcopration.setEmail(muncipalDTO.getEmail());
        muncipalcopration.setRoles(Collections.singletonList("MUNCIPAL"));

        muncipalRepository.save(muncipalcopration);
        return "Succesfully Created";
    }
}
