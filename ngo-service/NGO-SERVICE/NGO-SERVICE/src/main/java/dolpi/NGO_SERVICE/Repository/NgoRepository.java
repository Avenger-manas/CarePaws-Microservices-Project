package dolpi.NGO_SERVICE.Repository;

import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NgoRepository extends MongoRepository<RegisterNgo,String> {
    RegisterNgo findByUsername(String username);

    RegisterNgo findByEmail(String email);

    List<RegisterNgo> findByCity(String city);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
