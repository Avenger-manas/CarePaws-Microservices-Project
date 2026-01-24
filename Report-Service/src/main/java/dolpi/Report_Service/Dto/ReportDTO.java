package dolpi.Report_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    @NonNull
    private  String userid;

    @NonNull
    private String name;

    @NonNull
    private String mobilenumber;

    @NonNull
    private String location_address;

    @NonNull
    private String city;

    @NonNull
    private String pincode;

    @NonNull
    private String description;

}
