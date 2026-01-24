package dolpi.NGO_SERVICE.Repository;

import dolpi.NGO_SERVICE.Entity.NgoNotification;
import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoNotificationRepo extends MongoRepository<NgoNotification,String> {
}
