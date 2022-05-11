package pl.nqriver.homebudget.services.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;
import pl.nqriver.homebudget.exceptions.UserAlreadyExistsException;
import pl.nqriver.homebudget.exceptions.UserNotFoundException;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.UserDetailsDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserDetailsServiceImplIntegrationTest extends IntegrationTestDatabaseInitializer {


    @Test
    void shouldLoadExistingUser() {
        //given
        initDefaultUserInDatabase();

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(FIRST_USERNAME);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(FIRST_USERNAME);
        assertThat(userDetails.getPassword()).isEqualTo(FIRST_USER_PASSWORD);
    }

    @Test
    void shouldThrowExceptionCreatingUserIfUserAlreadyExistsInDatabase() {
        //given
        initDefaultUserInDatabase();
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .username(FIRST_USERNAME)
                .password(FIRST_USER_PASSWORD)
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
        initDefaultUserInDatabase();
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
        UserEntity user = initDefaultUserInDatabase();
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
