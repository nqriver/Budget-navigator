package pl.nqriver.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.homebudget.service.AuthenticationService;
import pl.nqriver.homebudget.service.dto.AuthenticationJwtToken;
import pl.nqriver.homebudget.service.dto.AuthenticationUserDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public AuthenticationJwtToken getAuthenticationToken(@RequestBody AuthenticationUserDto authenticationUserDto) {
        return authenticationService.createAuthenticationToken(authenticationUserDto);
    }
}
