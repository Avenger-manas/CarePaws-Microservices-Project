package dolpi.CarePaws.Repository;

import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoRepository extends MongoRepository<NgoEntity,String> {
    NgoEntity findByUsername(String username);

    NgoEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
