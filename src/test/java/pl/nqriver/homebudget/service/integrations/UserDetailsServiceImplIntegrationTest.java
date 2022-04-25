package pl.nqriver.homebudget.service.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;
import pl.nqriver.homebudget.exceptions.UserAlreadyExistsException;
import pl.nqriver.homebudget.exceptions.UserNotFoundException;
import pl.nqriver.homebudget.repository.UserRepository;
import pl.nqriver.homebudget.repository.entities.UserEntity;
import pl.nqriver.homebudget.service.UserDetailsServiceImpl;
import pl.nqriver.homebudget.service.dto.UserDetailsDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserDetailsServiceImplIntegrationTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "pass";

    @Test
    void shouldLoadExistingUser() {
        //given
        initDatabaseWithOneUser();

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void shouldThrowExceptionCreatingUserIfUserAlreadyExistsInDatabase() {
        //given
        initDatabaseWithOneUser();
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        //when //then
        assertThatThrownBy(() -> userDetailsService.saveUser(userDetailsDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }

    @Test
    void shouldThrowExceptionGettingUserIfUserDoesNotExistInDatabase() {
        //given
        String nonExistentUser = "nonExistentUser";

        //when //then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(nonExistentUser))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

    void initDatabaseWithOneUser() {
        UserEntity userEntity = UserEntity.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        userRepository.save(userEntity);
    }
}
