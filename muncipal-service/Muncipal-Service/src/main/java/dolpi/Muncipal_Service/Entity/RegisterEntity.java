package dolpi.Muncipal_Service.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection ="muncipal")
public class RegisterEntity {

    @Id
    private String id;

    @NonNull
    private String username;         //ngoname

    @NonNull
    private String municipalName;     // Bareilly Municipal Corporation

    @NonNull
    private String state;              // Uttar Pradesh

    @NonNull
    private String city;               // Bareilly

    @NonNull
    private String officeAddress;      //golden fly colony

    @NonNull
    private String email;      // official mail

    @NonNull
    private String helplineNumber;    //9870xxxxxxx

    @NonNull
    private String registrationCode;   // GOV / MUNI CODE (unique)

    @NonNull
    private String description;         // our goal is rescure injured stray animals

}
