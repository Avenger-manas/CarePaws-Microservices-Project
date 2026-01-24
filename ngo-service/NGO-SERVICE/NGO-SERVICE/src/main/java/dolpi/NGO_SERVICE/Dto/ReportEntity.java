package dolpi.NGO_SERVICE.Dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class ReportEntity {

    private String id;

    private String name;


    private String mobilenumber;


    private String location_address;


    private String city;


    private String pincode;


    private String description;

    private String imagepath;

    private LocalDateTime createdAt;
}
