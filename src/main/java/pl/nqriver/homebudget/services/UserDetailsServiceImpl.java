package pl.nqriver.homebudget.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.UserAlreadyExistsException;
import pl.nqriver.homebudget.exceptions.UserNotFoundException;
import pl.nqriver.homebudget.mappers.UserMapper;
import pl.nqriver.homebudget.repositories.UserRepository;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserLogInfoService userLogInfoService;
    private final AssetsService assetService;

    public UserDetailsServiceImpl(UserRepository userRepository, UserMapper userMapper, UserLogInfoService userLogInfoService, AssetsService assetService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userLogInfoService = userLogInfoService;
        this.assetService = assetService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Searching user: {}", username);
        UserEntity entity = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return new User(entity.getUsername(), entity.getPassword(), Collections.emptyList());
    }

    public Long saveUser(UserDetailsDto userDetailsDto) {
        validateIfUserExists(userDetailsDto);
        var userEntity = userMapper.fromDtoToEntity(userDetailsDto);
        var savedEntity = userRepository.save(userEntity);
        LOGGER.info("User saved {}", savedEntity);
        return savedEntity.getId();
    }

    private void validateIfUserExists(UserDetailsDto userDetailsDto) {
        if (userRepository.findByUsername(userDetailsDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    @Transactional
    public void deleteUser() {
        UserEntity loggedUserEntity = userLogInfoService.getLoggedUserEntity();
        assetService.deleteAssetsByUser(loggedUserEntity);
        userRepository.delete(loggedUserEntity);
    }
}
