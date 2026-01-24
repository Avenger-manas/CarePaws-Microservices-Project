package dolpi.CarePaws.Repository;

import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Entity.NgoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuncipalRepository extends MongoRepository<Muncipalcopration,String> {
    Muncipalcopration findByUsername(String username);

    Muncipalcopration findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
