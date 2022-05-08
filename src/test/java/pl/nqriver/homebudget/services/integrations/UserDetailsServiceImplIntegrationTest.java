package pl.nqriver.homebudget.services.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;
import pl.nqriver.homebudget.exceptions.UserAlreadyExistsException;
import pl.nqriver.homebudget.exceptions.UserNotFoundException;
import pl.nqriver.homebudget.repositories.AssetsRepository;
import pl.nqriver.homebudget.repositories.UserRepository;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.UserDetailsServiceImpl;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@WithMockUser(username = "username", password = "pass")
public class UserDetailsServiceImplIntegrationTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsRepository assetsRepository;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "pass";


    @Test
    void shouldLoadExistingUser() {
        //given
        initDatabaseWithOneUser();

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void shouldThrowExceptionCreatingUserIfUserAlreadyExistsInDatabase() {
        //given
        initDatabaseWithOneUser();
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        //when //then
        assertThatThrownBy(() -> userDetailsService.saveUser(userDetailsDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }

    @Test
    void shouldThrowExceptionGettingUserIfUserDoesNotExistInDatabase() {
        //given
        String nonExistentUser = "nonExistentUser";

        //when //then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(nonExistentUser))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

    @Test
    void shouldDeleteUserWithoutAssetsFromDatabase() {
        //given
        initDatabaseWithOneUser();
        var numberOfUsersInDatabase = userRepository.findAll().size();

        List<UserEntity> usersInDatabaseBeforeDelete = userRepository.findAll();
        assertThat(usersInDatabaseBeforeDelete).hasSize(1);
        // when

        userDetailsService.deleteUser();

        // then
        List<UserEntity> usersInDatabaseAfterDelete = userRepository.findAll();
        assertThat(usersInDatabaseAfterDelete).hasSize(0);
    }


    @Test
    void shouldDeleteUserWithOneAssetFromDatabase() {
        // given
        UserEntity user = initDatabaseWithOneUser();
        initDatabaseWithAssetForUser(user);

        List<AssetEntity> assetsInDatabaseBeforeDelete = assetsRepository.findAll();
        List<UserEntity> usersInDatabaseBeforeDelete = userRepository.findAll();

        assertThat(assetsInDatabaseBeforeDelete).hasSize(1);
        assertThat(usersInDatabaseBeforeDelete).hasSize(1);


        // when
        userDetailsService.deleteUser();

        //then

        List<AssetEntity> assetsInDatabaseAfterDelete = assetsRepository.findAll();
        List<UserEntity> usersInDatabaseAfterDelete = userRepository.findAll();

        assertThat(assetsInDatabaseAfterDelete).hasSize(0);
        assertThat(usersInDatabaseAfterDelete).hasSize(0);


    }

    UserEntity initDatabaseWithOneUser() {
        UserEntity userEntity = UserEntity.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        return userRepository.save(userEntity);
    }

    void initDatabaseWithAssetForUser(UserEntity userEntity) {
        AssetEntity assetEntity = AssetEntity.builder()
                .incomeDate(Instant.now())
                .category(AssetCategory.BONUS)
                .amount(BigDecimal.TEN)
                .user(userEntity)
                .build();
        assetsRepository.save(assetEntity);
    }
}
