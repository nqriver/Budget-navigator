package pl.nqriver.homebudget.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nqriver.homebudget.enums.AssetValidatorEnum;
import pl.nqriver.homebudget.exceptions.AssetIncompleteException;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repositories.AssetsRepository;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.services.dtos.AssetDto;
import pl.nqriver.homebudget.validators.AssetValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {


    @Mock
    private AssetsRepository assetsRepository;

    @Mock
    private UserLogInfoService userLogInfoService;

    private AssetsMapper assetsMapper = new AssetsMapper();

    private AssetValidator assetValidator = new AssetValidator();

    private AssetsService assetsService;

    @BeforeEach
    void setUp() {
        assetsService = new AssetsService(
                assetsRepository,
                assetsMapper,
                assetValidator,
                userLogInfoService);
    }

    @Test
    void shouldReturnListWithOneElementIfThereWasOneSavedAssetInDatabaseBefore() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        Instant now = Instant.now();
        AssetDto assetDto = AssetDto.builder().amount(asset).incomeDate(now).build();

        assetsService.setAsset(assetDto);
        AssetEntity assetEntity = AssetEntity.builder().amount(asset).incomeDate(now).build();
        List<AssetEntity> assetsList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetsList);

        // when
        var result = assetsService.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(1)
                .containsExactly(AssetDto.builder().amount(asset).incomeDate(now).build());
    }

    @Test
    void shouldReturnListOfThreeIntegersIfThereWereThreeSavedAssetsInDatabaseBefore() {
        // given
        var assetOneAmount = BigDecimal.ONE;
        var assetTwoAmount = BigDecimal.valueOf(2);
        var assetThreeAmount = BigDecimal.valueOf(3);

        var assetEntityOne = AssetEntity.builder()
                .amount(assetOneAmount).build();
        var assetEntityTwo = AssetEntity.builder()
                .amount(assetTwoAmount).build();
        var assetEntityThree = AssetEntity.builder()
                .amount(assetThreeAmount).build();

        List<AssetEntity> assetsList = List.of(assetEntityOne, assetEntityTwo, assetEntityThree);
        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetsList);
        // when
        var result = assetsService.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(
                        AssetDto.builder().amount(assetOneAmount).build(),
                        AssetDto.builder().amount(assetTwoAmount).build(),
                        AssetDto.builder().amount(assetThreeAmount).build()

                );
    }

    @Test
    void shouldVerifyIfRepositorySaveWasCalledOneTime() {
        //given
        BigDecimal asset = BigDecimal.ONE;
        Instant now = Instant.now();
        var assetDto = AssetDto.builder().amount(asset).incomeDate(now).build();
        var assetEntity = AssetEntity.builder().amount(asset).incomeDate(now).build();

        //when
        assetsService.setAsset(assetDto);

        //then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(assetEntity);
    }

    @Test
    void shouldThrowExceptionWhenAmountInAssetDtoIsNull() {
        //given
        Instant now = Instant.now();
        var assetDto = AssetDto.builder().incomeDate(now).build(); // no amount field set

        //when
        AssetIncompleteException result = assertThrows(AssetIncompleteException.class,
                () -> assetsService.setAsset(assetDto));
        //then
        assertEquals(AssetValidatorEnum.NO_AMOUNT.getMessage(), result.getMessage());

    }

    @Test
    void shouldVerifyIfTheRepositoryUpdateWasCalled() {
        //given
        BigDecimal asset = BigDecimal.ONE;
        var entity = AssetEntity.builder().amount(asset).build();
        var assetDto = AssetDto.builder().amount(asset).build();

        Mockito.when(assetsRepository.findById(any())).thenReturn(Optional.of(entity));
        //when

        assetsService.updateAsset(assetDto);

        //then

        Mockito.verify(assetsRepository, Mockito.times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldThrowWhenIncomeDateIsNull() {
        //given
        AssetDto assetDto = AssetDto.builder().amount(BigDecimal.ONE).build();

        //when
        var result = assertThrows(AssetIncompleteException.class,
                () -> assetsService.setAsset(assetDto));

        //then
        assertEquals(AssetValidatorEnum.NO_INCOME_DATE.getMessage(), result.getMessage());
    }
}