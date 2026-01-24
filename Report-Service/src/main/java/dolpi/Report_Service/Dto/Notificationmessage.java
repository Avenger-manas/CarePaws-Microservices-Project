package dolpi.Report_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificationmessage {
    private String reported_id;

    private String city;


}
