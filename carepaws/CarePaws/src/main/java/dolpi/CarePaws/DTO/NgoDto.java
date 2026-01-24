package dolpi.CarePaws.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoDto {
    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String password;
}
