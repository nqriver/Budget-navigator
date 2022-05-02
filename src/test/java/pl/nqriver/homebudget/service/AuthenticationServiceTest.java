package pl.nqriver.homebudget.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;
import pl.nqriver.homebudget.exceptions.InvalidUsernameOrPasswordException;
import pl.nqriver.homebudget.service.dto.AuthenticationJwtToken;
import pl.nqriver.homebudget.service.dto.UserDetailsDto;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    public static final String USER_PASSWORD = "userPassword";
    public static final String USERNAME = "username";
    private AuthenticationService authenticationService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        JWTService jwtService = new JWTService();
        authenticationService = new AuthenticationService(
                userDetailsService,
                jwtService,
                authenticationManager
        );
    }

    @Test
    void shouldReturnTokenWhenUserAndPasswordMatch() {
        // given
        String expectedTokenHeader = "eyJhbGciOiJIUzI1NiJ9";
        UserDetailsDto authenticationUser = UserDetailsDto.builder()
                .username(USERNAME)
                .password(USER_PASSWORD)
                .build();

        Collection authorities = Collections.emptyList();
        UserDetails userDetails = new User(USERNAME, USER_PASSWORD, authorities);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME, USER_PASSWORD);

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);


        // when
        AuthenticationJwtToken result = authenticationService.createAuthenticationToken(authenticationUser);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNotNull();
        assertThat(result.getToken()).startsWith(expectedTokenHeader);


    }

    @Test
    void shouldThrowInvalidUsernameOrPasswordExceptionWhenUsernameIsIncorrect() {
        // given

        UserDetailsDto authenticationUser = UserDetailsDto.builder()
                .username(USERNAME)
                .password(USER_PASSWORD)
                .build();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME, USER_PASSWORD);
        when(authenticationManager.authenticate(authenticationToken)).thenThrow(BadCredentialsException.class);


        // when //then
        assertThatThrownBy(() -> authenticationService.createAuthenticationToken(authenticationUser))
                .isInstanceOf(InvalidUsernameOrPasswordException.class)
                .hasMessage(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());


    }
}