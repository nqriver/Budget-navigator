package pl.nqriver.homebudget.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationJwtToken {
    private final String token;
}
