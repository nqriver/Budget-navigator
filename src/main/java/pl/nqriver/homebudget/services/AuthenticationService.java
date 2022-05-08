package pl.nqriver.homebudget.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.InvalidUsernameOrPasswordException;
import pl.nqriver.homebudget.services.dtos.AuthenticationJwtToken;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;


@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserDetailsServiceImpl userDetailsServiceImpl, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsServiceImpl;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationJwtToken createAuthenticationToken(UserDetailsDto userDetailsDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetailsDto.getUsername(), userDetailsDto.getPassword()
            ));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new InvalidUsernameOrPasswordException();
        }

        var userDetails = userDetailsService.loadUserByUsername(userDetailsDto.getUsername());
        var jwtToken = jwtService.generateJWTToken(userDetails);

        return new AuthenticationJwtToken(jwtToken);
    }
}
