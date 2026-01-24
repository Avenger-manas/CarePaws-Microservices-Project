package dolpi.CarePaws.Service;

import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsTest {
    @InjectMocks
    private MyUserDetails myUserDetails;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MuncipalRepository muncipalRepository;

    @Mock
    private NgoRepository ngoRepository;

    // CASE 1: User table
    @Test
    void shouldLoadUserFromUserTable() {

        UserEntity user = new UserEntity();
        user.setUsername("user1");
        user.setPassword("encodedPass");
        user.setRoles(List.of("USER"));

        when(userRepository.findByUsername("user1"))
                .thenReturn(user);

        UserDetails details =
                myUserDetails.loadUserByUsername("user1");

        assertEquals("user1", details.getUsername());
        assertEquals("encodedPass", details.getPassword());

        verify(userRepository).findByUsername("user1");
        verifyNoInteractions(muncipalRepository, ngoRepository);
    }

    // CASE 2: Muncipal table
    @Test
    void shouldLoadUserFromMuncipalTable() {

        when(userRepository.findByUsername("mun1"))
                .thenReturn(null);

        Muncipalcopration muncipal = new Muncipalcopration();
        muncipal.setUsername("mun1");
        muncipal.setPassword("encodedMun");
        muncipal.setRoles(List.of("MUNCIPAL"));

        when(muncipalRepository.findByUsername("mun1"))
                .thenReturn(muncipal);

        UserDetails details =
                myUserDetails.loadUserByUsername("mun1");

        assertEquals("mun1", details.getUsername());
        assertEquals("encodedMun", details.getPassword());
    }

    //  CASE 3: NGO table
    @Test
    void shouldLoadUserFromNgoTable() {

        when(userRepository.findByUsername("ngo1"))
                .thenReturn(null);
        when(muncipalRepository.findByUsername("ngo1"))
                .thenReturn(null);

        NgoEntity ngo = new NgoEntity();
        ngo.setUsername("ngo1");
        ngo.setPassword("encodedNgo");
        ngo.setRoles(List.of("NGO"));

        when(ngoRepository.findByUsername("ngo1"))
                .thenReturn(ngo);

        UserDetails details =
                myUserDetails.loadUserByUsername("ngo1");

        assertEquals("ngo1", details.getUsername());
        assertEquals("encodedNgo", details.getPassword());
    }

    // CASE 4: Not found anywhere
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByUsername("unknown"))
                .thenReturn(null);
        when(muncipalRepository.findByUsername("unknown"))
                .thenReturn(null);
        when(ngoRepository.findByUsername("unknown"))
                .thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> myUserDetails.loadUserByUsername("unknown")
        );
    }

}
