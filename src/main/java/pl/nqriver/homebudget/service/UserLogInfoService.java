package pl.nqriver.homebudget.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.UserNotFoundException;
import pl.nqriver.homebudget.repository.UserRepository;
import pl.nqriver.homebudget.repository.entities.UserEntity;

@Service
public class UserLogInfoService {

    private final UserRepository userRepository;

    public UserLogInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserEntity getLoggedUserEntity() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = ((User)authentication.getPrincipal()).getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
