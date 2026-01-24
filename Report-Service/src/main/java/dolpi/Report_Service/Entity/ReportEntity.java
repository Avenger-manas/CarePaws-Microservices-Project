package dolpi.Report_Service.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection ="report")
public class ReportEntity {

    @Id
    private String id;

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

    private String imagepath;

    private LocalDateTime createdAt;

    public ReportEntity(){}


}
