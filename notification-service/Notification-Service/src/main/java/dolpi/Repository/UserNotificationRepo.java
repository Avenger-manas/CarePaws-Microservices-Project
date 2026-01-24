package dolpi.Repository;

import dolpi.Entity.UserNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepo extends MongoRepository<UserNotification,String> {
}
