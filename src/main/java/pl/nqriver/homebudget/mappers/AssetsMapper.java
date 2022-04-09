package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.service.dto.AssetDto;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto assetsDto) {
        return AssetEntity.builder()
                .amount(assetsDto.getAmount())
                .build();
    }

    public AssetDto fromEntityToDto(AssetEntity assetEntity) {
        return AssetDto.builder()
                .id(assetEntity.getId())
                .amount(assetEntity.getAmount())
                .build();
    }
}
