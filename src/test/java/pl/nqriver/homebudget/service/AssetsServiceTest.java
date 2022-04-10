package pl.nqriver.homebudget.service;

import liquibase.pro.packaged.A;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.service.dto.AssetDto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {   
   

    @Mock
    private AssetsRepository assetsRepository;

    @Autowired
    private AssetsMapper assetsMapper;

    private AssetsService assetsService;

    @BeforeEach
    void setUp() {
        assetsService = new AssetsService(assetsRepository, assetsMapper);
    }

    @Test
    void shouldReturnListWithOneElementIfThereWasOneSavedAssetInDatabaseBefore() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        AssetDto assetDto = AssetDto.builder().amount(asset).build();

        assetsService.setAsset(assetDto);
        AssetEntity assetEntity = AssetEntity.builder().amount(asset).build();
        List<AssetEntity> assetsList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetsList);

        // when
        var retrievedAssets = assetsService.getAllAssets();

        // then
        var listOfAssets = retrievedAssets.getAssets();
        Assertions.assertThat(listOfAssets)
                .hasSize(1)
                .containsExactly(asset.intValue());
    }

    @Test
    void shouldReturnListOfThreeIntegersIfThereWereThreeSavedAssetsInDatabaseBefore() {
        // given
        var assetOneAmount = 1;
        var assetTwoAmount = 2;
        var assetThreeAmount = 3;

        var assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.valueOf(assetOneAmount)).build();
        var assetEntityTwo = AssetEntity.builder()
                .amount(BigDecimal.valueOf(assetTwoAmount)).build();
        var assetEntityThree = AssetEntity.builder()
                .amount(BigDecimal.valueOf(assetThreeAmount)).build();

        List<AssetEntity> assetsList = List.of(assetEntityOne, assetEntityTwo, assetEntityThree);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetsList);
        // when
        var result = assetsService.getAllAssets();

        // then
        var listOfAssets = result.getAssets();
        Assertions.assertThat(listOfAssets)
                .hasSize(3)
                .containsExactly(assetOneAmount, assetTwoAmount, assetThreeAmount);
    }

    @Test
    void shouldVerifyIfRepositorySaveWasCalledOneTime() {
        //given
        BigDecimal asset = BigDecimal.ONE;
        var assetDto = AssetDto.builder().amount(asset).build();
        var assetEntity = AssetEntity.builder().amount(asset).build();

        //when
        assetsService.setAsset(assetDto);

        //then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(assetEntity);
    }
}