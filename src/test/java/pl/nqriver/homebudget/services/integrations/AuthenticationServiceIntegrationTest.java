package pl.nqriver.homebudget.services.integrations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;
import pl.nqriver.homebudget.exceptions.InvalidUsernameOrPasswordException;
import pl.nqriver.homebudget.services.AuthenticationService;
import pl.nqriver.homebudget.services.JWTService;
import pl.nqriver.homebudget.services.UserDetailsServiceImpl;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
public class AuthenticationServiceIntegrationTest {

    public static final String USERNAME = "username123";
    public static final String PASSWORD = "123username";
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
                userDetailsService,
                jwtService,
                authenticationManager
        );
    }

    @Test
    void shouldThrowInvalidUsernameOrPasswordExceptionWhenUsernameIsIncorrct() {
        //given
        initUserInDatabase();


        UserDetailsDto dto = UserDetailsDto.builder()
                .username("incorrectUsername")
                .password("whatever")
                .build();

        //when //then
        assertThatThrownBy(() -> authenticationService.createAuthenticationToken(dto))
                .isInstanceOf(InvalidUsernameOrPasswordException.class)
                .hasMessage(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());

    }

    @Test
    void shouldThrowInvalidUsernameOrPasswordExceptionWhenPasswordIsIncorrect() {
        //given
        initUserInDatabase();


        UserDetailsDto dto = UserDetailsDto.builder()
                .username(USERNAME)
                .password("incorrectPassword")
                .build();

        //when //then
        assertThatThrownBy(() -> authenticationService.createAuthenticationToken(dto))
                .isInstanceOf(InvalidUsernameOrPasswordException.class)
                .hasMessage(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());



    }

    private void initUserInDatabase() {
        UserDetailsDto dto = UserDetailsDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        userDetailsService.saveUser(dto);
    }

}
