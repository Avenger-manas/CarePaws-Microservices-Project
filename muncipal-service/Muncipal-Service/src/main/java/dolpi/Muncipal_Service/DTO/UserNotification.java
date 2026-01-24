package dolpi.Muncipal_Service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotification {

    private  String userid;

    @JsonProperty("NGOanMNCPL_Name")
    private String NGOanMNCPL_Name;

    @JsonProperty("TIME_INFOan_DSC")
    private String TIME_INFOan_DSC;
}
