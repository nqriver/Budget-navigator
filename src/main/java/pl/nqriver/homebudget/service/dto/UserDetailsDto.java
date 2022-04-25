package pl.nqriver.homebudget.service.dto;

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
