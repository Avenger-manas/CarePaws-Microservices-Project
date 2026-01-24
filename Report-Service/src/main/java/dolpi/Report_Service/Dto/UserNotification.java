package dolpi.Report_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {

    private  String userid;

    @NonNull
    private String NGOanMNCPL_Name;

    @NonNull
    private String TIME_INFOan_DSC;
}
