package pl.nqriver.homebudget.service.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.repository.UserRepository;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.repository.entities.UserEntity;
import pl.nqriver.homebudget.service.AssetsService;
import pl.nqriver.homebudget.service.dto.AssetDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "123username")
public class AssetServiceIntegrationTest {

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private UserRepository userRepository;

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

    private UserEntity initUserInDatabase() {
        var user = UserEntity.builder()
                .username("user123")
                .password("123user")
                .build();
        return userRepository.save(user);
    }


    private void initDatabase() {
        UserEntity userEntity = initUserInDatabase();
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
