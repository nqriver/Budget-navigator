package pl.nqriver.homebudget.services.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssetServiceIntegrationTest extends IntegrationTestDatabaseInitializer {

    @Test
    void shouldReturnListOfAssetsWithOneCategory() {
        //given
        initDatabaseWithDefaultUserAndAssets();
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
        initDatabaseWithDefaultUserAndAssets();
        initDatabaseWithSecondUserAndAssets();

        //when
        List<AssetDto> allAssetsOfLoggedUser = assetsService.getAllAssets();

        //then
        Assertions.assertThat(allAssetsOfLoggedUser).hasSize(3);
    }


    @Test
    void shouldDeleteAllAssetsOfUser() {
        // given
        initDatabaseWithDefaultUserAndAssets();
        initDatabaseWithSecondUserAndAssets();

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

}
