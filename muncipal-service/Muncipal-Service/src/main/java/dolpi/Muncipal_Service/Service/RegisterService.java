package dolpi.Muncipal_Service.Service;

import dolpi.Muncipal_Service.DTO.RegisterDTO;
import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    MuncipalCoprationRepo muncipalCoprationRepo;

    public ResponseEntity<?> registermuncipal(String username, RegisterDTO registerDTO,String id){
        RegisterEntity register=new RegisterEntity();

        register.setId(id);
        register.setUsername(username);
        register.setMunicipalName(registerDTO.getMunicipalName());
        register.setCity(registerDTO.getCity());
        register.setHelplineNumber(registerDTO.getHelplineNumber());
        register.setOfficeAddress(registerDTO.getOfficeAddress());
        register.setEmail(registerDTO.getEmail());
        register.setRegistrationCode(registerDTO.getRegistrationCode());
        register.setState(registerDTO.getState());
        register.setDescription(registerDTO.getDescription());

        muncipalCoprationRepo.save(register);

        return new ResponseEntity<>("Sucsessfully Register", HttpStatus.CREATED);

    }
}
