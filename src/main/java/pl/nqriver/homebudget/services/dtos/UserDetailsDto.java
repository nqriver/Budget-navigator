package pl.nqriver.homebudget.services.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDetailsDto {

    private String username;
    private String password;
}
