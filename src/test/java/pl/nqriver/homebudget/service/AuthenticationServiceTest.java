package pl.nqriver.homebudget.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nqriver.homebudget.service.dto.AuthenticationJwtToken;
import pl.nqriver.homebudget.service.dto.UserDetailsDto;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

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

        String password = "userPassword";
        String username = "username";
        String expectedTokenHeader = "eyJhbGciOiJIUzI1NiJ9";
        UserDetailsDto authenticationUser = UserDetailsDto.builder()
                .username(username)
                .password(password)
                .build();

        Collection authorities = Collections.emptyList();
        UserDetails userDetails = new User(username, password , authorities);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Mockito.when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);


        // when
        AuthenticationJwtToken result = authenticationService.createAuthenticationToken(authenticationUser);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNotNull();
        assertThat(expectedTokenHeader).isSubstringOf(result.getToken());


    }
}