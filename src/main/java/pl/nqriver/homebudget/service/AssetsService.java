package pl.nqriver.homebudget.service;


import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.repository.entities.AssetEntity;
import pl.nqriver.homebudget.service.dto.AssetDto;
import pl.nqriver.homebudget.service.dto.AssetsDto;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AssetsService {


    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
    }

    public AssetsDto getAllAssets() {
        var allAssets = assetsRepository.findAll();
        var assetsAmounts = allAssets.stream()
                .map(asset -> asset.getAmount().intValue())
                .collect(Collectors.toList());
        return AssetsDto.builder().assets(assetsAmounts).build();
    }

    public void setAsset(int asset) {
        var assetDto = AssetDto.builder().amount(BigDecimal.valueOf(asset)).build();
        var assetEntity = assetsMapper.fromDtoToEntity(assetDto);
        assetsRepository.save(assetEntity);
    }
}
