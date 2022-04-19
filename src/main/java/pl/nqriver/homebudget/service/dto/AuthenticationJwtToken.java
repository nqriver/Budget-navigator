package pl.nqriver.homebudget.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationJwtToken {
    private final String token;
}
