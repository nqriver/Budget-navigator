package pl.nqriver.homebudget.controllers;

import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.services.AuthenticationService;
import pl.nqriver.homebudget.services.UserDetailsServiceImpl;
import pl.nqriver.homebudget.services.dtos.AuthenticationJwtToken;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;


    public AuthenticationController(AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public AuthenticationJwtToken getAuthenticationToken(@RequestBody UserDetailsDto userDetailsDto) {
        return authenticationService.createAuthenticationToken(userDetailsDto);
    }

    @PostMapping
    public Long setUserDetails(@RequestBody UserDetailsDto userDetailsDto) {
        return userDetailsService.saveUser(userDetailsDto);

    }

    @DeleteMapping
    public void deleteUser() {
        userDetailsService.deleteUser();
    }
}
