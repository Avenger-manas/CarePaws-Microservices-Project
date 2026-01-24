package dolpi.NGO_SERVICE.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection ="ngoNotification")
public class NgoNotification {
    @Id
    private String id;

    @NonNull
    private String ngoId;

    @NonNull
    private String submissionId;

}
