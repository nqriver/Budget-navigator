package pl.nqriver.homebudget.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;

@SpringBootTest
class AssetsServiceTest {

    @Autowired
    private AssetsService assetsService;

    @Test
    void shouldSaveAssetAndReturnListWithOneElementIfThereWasNoSavedAssetsBefore() {
        // given
        int asset = 1;

        assetsService.setAsset(asset);
        // when
        var retrievedAssets = assetsService.getAllAssets();
        // then

        var listOfAssets = retrievedAssets.getAssets();
        Assertions.assertThat(listOfAssets)
                .hasSize(1)
                .containsExactly(asset);
    }

    @Test
    void shouldSaveAssetAndReturnListWithTwoElementsIfThereWasNoSavedAssetsBefore() {
        // given
        var assetOne = 1;
        var assetTwo = 2;
        var assetThree = 3;

        assetsService.setAsset(assetOne);
        assetsService.setAsset(assetTwo);
        assetsService.setAsset(assetThree);

        // when
        var result = assetsService.getAllAssets();

        // then
        var listOfAssets = result.getAssets();
        Assertions.assertThat(listOfAssets)
                .hasSize(3)
                .containsExactly(assetOne, assetTwo, assetThree);
    }
}