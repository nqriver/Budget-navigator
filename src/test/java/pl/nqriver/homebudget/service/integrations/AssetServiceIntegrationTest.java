package pl.nqriver.homebudget.service.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.service.AssetsService;
import pl.nqriver.homebudget.service.dto.AssetDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@Transactional
public class AssetServiceIntegrationTest {

    @Autowired
    AssetsRepository assetsRepository;

    @Autowired
    AssetsService assetsService;

    @Test
    void shouldReturnListOfAssetsWithOneCategory() {
        //given
        initDatabase();
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
        initDatabase();

        //when
        List<AssetDto> allAssetsInDb = assetsService.getAllAssets();

        //then
        Assertions.assertThat(allAssetsInDb).hasSize(3);
    }

    private void initDatabase() {
        AssetEntity assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .build();
        AssetEntity assetEntityTwo = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.LOAN_RETURNED)
                .build();
        AssetEntity assetEntityThree = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .build();

        assetsRepository.saveAll(List.of(
                assetEntityOne,
                assetEntityTwo,
                assetEntityThree
        ));
    }
}
