package dolpi.NGO_SERVICE.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection ="ngo")
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNgo {

    @Id
    private String id;

    @NonNull
    private String username;

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
