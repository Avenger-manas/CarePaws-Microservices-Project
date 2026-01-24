package dolpi.Report_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNgo {

    private String id;

    private String userid;


    private String username;


    private String ngoname;


    private String address;


    private String city;


    private String email;


    private String phonenumber;


    private String animaltype;

    private String description;

}
