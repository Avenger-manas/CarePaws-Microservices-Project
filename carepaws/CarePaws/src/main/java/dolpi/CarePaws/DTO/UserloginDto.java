package dolpi.CarePaws.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserloginDto {

    @NonNull
    String username;

    @NonNull
    String password;
}
