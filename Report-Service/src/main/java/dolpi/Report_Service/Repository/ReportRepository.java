package dolpi.Report_Service.Repository;

import dolpi.Report_Service.Entity.ReportEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<ReportEntity,String> {
//    ReportEntity findByUserId(String id);
}
