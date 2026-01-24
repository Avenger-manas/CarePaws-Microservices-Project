package dolpi.Report_Service.Dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class RegisterEntity {

    private String id;


    private String username;         //ngoname


    private String municipalName;     // Bareilly Municipal Corporation


    private String state;              // Uttar Pradesh


    private String city;               // Bareilly


    private String officeAddress;      //golden fly colony


    private String email;      // official mail


    private String helplineNumber;    //9870xxxxxxx


    private String registrationCode;   // GOV / MUNI CODE (unique)


    private String description;         // our goal is rescure injured stray animals
}
