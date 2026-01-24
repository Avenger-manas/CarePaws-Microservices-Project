package dolpi.CarePaws.Service;

import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userdatabase; // User Entity ka repository

    @Autowired
    private MuncipalRepository muncipalRepository; // Hospital Entity ka repository

    @Autowired
    private NgoRepository ngoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1️Pehle User table me check karo
        UserEntity user = userdatabase.findByUsername(username);
        if (user != null ) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // already encoded
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

        // 2 Agar User nahi mila to Muncipal table me check karo
        Muncipalcopration muncipalcopration = muncipalRepository.findByUsername(username);
        if (muncipalcopration!= null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(muncipalcopration.getUsername())
                    .password(muncipalcopration.getPassword()) // already encoded
                    .roles(muncipalcopration.getRoles().toArray(new String[0]))
                    .build();
        }

        NgoEntity ngos = ngoRepository.findByUsername(username);
        if (ngos != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(ngos.getUsername())
                    .password(ngos.getPassword()) // already encoded
                    .roles(ngos.getRoles().toArray(new String[0]))
                    .build();
        }

        // 3 Dono me nahi mila to exception
        throw new UsernameNotFoundException("No user or hospital found with username: " + username);
    }
}
