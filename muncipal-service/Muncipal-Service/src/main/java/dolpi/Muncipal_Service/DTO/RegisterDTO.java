package dolpi.Muncipal_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

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
