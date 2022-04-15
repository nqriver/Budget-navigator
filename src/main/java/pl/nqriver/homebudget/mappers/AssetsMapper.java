package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.service.dto.AssetDto;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto assetsDto) {
        return AssetEntity.builder()
                .amount(assetsDto.getAmount())
                .incomeDate(assetsDto.getIncomeDate())
                .category(assetsDto.getCategory())
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
