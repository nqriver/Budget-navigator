package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.AssetDto;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto assetsDto, UserEntity userEntity) {
        return AssetEntity.builder()
                .amount(assetsDto.getAmount())
                .incomeDate(assetsDto.getIncomeDate())
                .category(assetsDto.getCategory())
                .user(userEntity)
                .build();
    }

    public AssetDto fromEntityToDto(AssetEntity assetEntity) {
        return AssetDto.builder()
                .id(assetEntity.getId())
                .amount(assetEntity.getAmount())
                .incomeDate(assetEntity.getIncomeDate())
                .category(assetEntity.getCategory())
                .build();
    }
}
