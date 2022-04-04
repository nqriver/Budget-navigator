package pl.nqriver.homebudget.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssetsServiceTest {

    @Test
    void shouldSaveAssetAndReturnListWithOneElementIfThereWasNoSavedAssetsBefore() {
        // given
        var assetsService = new AssetsService();
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
        var service = new AssetsService();
        service.setAsset(assetOne);
        service.setAsset(assetTwo);
        service.setAsset(assetThree);

        // when
        var result = service.getAllAssets();

        // then
        var listOfAssets = result.getAssets();
        Assertions.assertThat(listOfAssets)
                .hasSize(3)
                .containsExactly(assetOne, assetTwo, assetThree);
    }
}