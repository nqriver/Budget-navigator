package pl.nqriver.homebudget.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.UserMapper;
import pl.nqriver.homebudget.repository.UserRepository;
import pl.nqriver.homebudget.repository.entities.UserEntity;
import pl.nqriver.homebudget.service.dto.UserDetailsDto;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Searching user: {}", username);
        UserEntity entity = userRepository
                .findByUsername(username)
                .orElseThrow(RuntimeException::new);

        return new User(entity.getUsername(), entity.getPassword(), Collections.emptyList());
    }

    public Long saveUser(UserDetailsDto userDetailsDto) {
        var userEntity = userMapper.fromDtoToEntity(userDetailsDto);
        var savedEntity = userRepository.save(userEntity);
        LOGGER.info("User saved {}", savedEntity);
        return savedEntity.getId();
    }
}
