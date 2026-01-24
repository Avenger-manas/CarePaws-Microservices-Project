package dolpi.Muncipal_Service.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection ="muncipalNotification")
public class MuncipalNotification {
    @Id
    private String id;

    @NonNull
    private String ngoId;

    @NonNull
    private String submissionId;
}
