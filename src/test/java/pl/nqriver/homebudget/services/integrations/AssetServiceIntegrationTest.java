package pl.nqriver.homebudget.services.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repositories.AssetsRepository;
import pl.nqriver.homebudget.repositories.UserRepository;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "123username")
public class AssetServiceIntegrationTest {

    public static final String FIRST_USERNAME = "user12345";
    public static final String SECOND_USERNAME = "user123";
    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnListOfAssetsWithOneCategory() {
        //given
        initDatabaseWithDefaultMockUser();
        var category = AssetCategory.OTHER;

        //when
        var allAssetsWithOneCategory = assetsService.getAssetsByCategory(category);

        //then

        allAssetsWithOneCategory.stream()
                .map(AssetDto::getCategory)
                .forEach(e -> Assertions.assertThat(e).isEqualTo(category));


    }

    @Test
    void shouldReturnListWhitThreeElements() {
        //given
        initDatabaseWithDefaultMockUser();
        initDatabaseWithSecondMockUser();

        //when
        List<AssetDto> allAssetsOfLoggedUser = assetsService.getAllAssets();

        //then
        Assertions.assertThat(allAssetsOfLoggedUser).hasSize(3);
    }

    private UserEntity initDefaultMockUser() {
        var user = UserEntity.builder()
                .username(FIRST_USERNAME)
                .password("123user")
                .build();
        return userRepository.save(user);
    }


    @Test
    void shouldDeleteAllAssetsOfUser() {
        // given
        initDatabaseWithDefaultMockUser();
        initDatabaseWithSecondMockUser();

        var userWithRemovedAssets = userRepository.findByUsername(FIRST_USERNAME).get();
        var userWithRetainedAssets = userRepository.findByUsername(SECOND_USERNAME).get();
        var numberOfAllAssetsToRemove = assetsRepository.getAssetEntitiesByUser(userWithRemovedAssets).size();
        var numberOfAllAssetsBeforeRemove = assetsRepository.findAll().size();
        var expectedNumberOfRemainedAssets = numberOfAllAssetsBeforeRemove - numberOfAllAssetsToRemove;

        // when
        assetsService.deleteAssetsByUser(userWithRemovedAssets);

        var remainedAssets = assetsRepository.findAll();

        // then
        Assertions.assertThat(remainedAssets)
                .hasSize(expectedNumberOfRemainedAssets);

        Set<UserEntity> ownersOfRemainedAssets = remainedAssets.stream()
                .map(AssetEntity::getUser)
                .collect(Collectors.toSet());

        Assertions.assertThat(ownersOfRemainedAssets)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(Collections.singleton(userWithRetainedAssets));

    }

    private void initDatabaseWithDefaultMockUser() {
        UserEntity userEntity = initDefaultMockUser();
        AssetEntity assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity assetEntityTwo = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.LOAN_RETURNED)
                .user(userEntity)
                .build();
        AssetEntity assetEntityThree = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(List.of(
                assetEntityOne,
                assetEntityTwo,
                assetEntityThree
        ));
    }

    private UserEntity initSecondMockUserInDatabase() {
        var user = UserEntity.builder()
                .username(SECOND_USERNAME)
                .password("12345user")
                .build();
        return userRepository.save(user);
    }


    private void initDatabaseWithSecondMockUser() {
        UserEntity userEntity = initSecondMockUserInDatabase();
        AssetEntity assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity assetEntityTwo = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.LOAN_RETURNED)
                .user(userEntity)
                .build();
        AssetEntity assetEntityThree = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(List.of(
                assetEntityOne,
                assetEntityTwo,
                assetEntityThree
        ));
    }
}
