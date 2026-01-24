package dolpi.Muncipal_Service.Repository;

import dolpi.Muncipal_Service.Entity.MuncipalNotification;
import dolpi.Muncipal_Service.Entity.RegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MuncipalRepo extends MongoRepository<MuncipalNotification,String> {
}
