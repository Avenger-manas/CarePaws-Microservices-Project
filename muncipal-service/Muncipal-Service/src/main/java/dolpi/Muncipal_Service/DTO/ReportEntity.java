package dolpi.Muncipal_Service.DTO;

import lombok.Data;

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
