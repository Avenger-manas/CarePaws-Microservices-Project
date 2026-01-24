package dolpi.Entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usernotification")
public class UserNotification {

    @Id
    private String Id;


    private  String userid;


    private String NGOanMNCPL_Name;


    private String TIME_INFOan_DSC;
}
