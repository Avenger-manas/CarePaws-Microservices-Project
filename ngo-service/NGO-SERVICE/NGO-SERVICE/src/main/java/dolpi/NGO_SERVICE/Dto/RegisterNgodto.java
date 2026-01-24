package dolpi.NGO_SERVICE.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNgodto {
    @NonNull
    private String ngoname;

    @NonNull
    private String address;

    @NonNull
    private String city;

    @NonNull
    private String email;

    @NonNull
    private String phonenumber;

    @NonNull
    private String animaltype;

    @NonNull
    private String description;

}
