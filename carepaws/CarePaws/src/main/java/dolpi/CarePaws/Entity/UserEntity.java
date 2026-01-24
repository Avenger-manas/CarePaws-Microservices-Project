package dolpi.CarePaws.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
//@Data
@Document(collection = "user")
public class UserEntity {
    @Id
    String id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String password;


    private List<String> roles;


    public void orElseGet(Object o) {
    }
}
