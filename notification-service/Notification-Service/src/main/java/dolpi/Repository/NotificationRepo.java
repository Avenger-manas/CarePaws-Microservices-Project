package dolpi.Repository;

import dolpi.Entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends MongoRepository<Notification,String> {
    List<Notification> findByNgoanmcplId(String ngoanmcplId);

    void deleteBySubmissionId(String submissionid);
}
