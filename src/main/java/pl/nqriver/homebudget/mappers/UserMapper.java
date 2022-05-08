package pl.nqriver.homebudget.mappers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

@Component
public class UserMapper {
    public UserEntity fromDtoToEntity(UserDetailsDto dto) {
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(encodePassword(dto.getPassword()))
                .build();
    }

    private String encodePassword(String password) {
        var salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
