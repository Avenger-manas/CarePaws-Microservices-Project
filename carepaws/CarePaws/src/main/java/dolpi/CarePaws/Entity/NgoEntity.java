package dolpi.CarePaws.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "ngo")
public class NgoEntity {
    @Id
    String id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String password;


    private List<String> roles;

}
