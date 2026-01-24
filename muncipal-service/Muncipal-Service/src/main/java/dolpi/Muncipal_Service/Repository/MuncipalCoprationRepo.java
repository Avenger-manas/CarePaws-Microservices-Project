package dolpi.Muncipal_Service.Repository;

import dolpi.Muncipal_Service.Entity.RegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuncipalCoprationRepo extends MongoRepository<RegisterEntity,String> {
    RegisterEntity findByUsername(String username);

    RegisterEntity findByEmail(String email);

    List<RegisterEntity> findByCity(String city);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
