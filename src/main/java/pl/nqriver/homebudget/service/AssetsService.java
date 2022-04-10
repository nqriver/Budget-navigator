package pl.nqriver.homebudget.service;


import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.service.dto.AssetDto;
import pl.nqriver.homebudget.service.dto.AssetsDto;
import pl.nqriver.homebudget.validators.AssetValidator;

import java.util.stream.Collectors;

@Service
public class AssetsService {


    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, AssetValidator assetValidator) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assetValidator = assetValidator;
    }

    public AssetsDto getAllAssets() {
        var allAssets = assetsRepository.findAll();
        var assetsAmounts = allAssets.stream()
                .map(asset -> asset.getAmount().intValue())
                .collect(Collectors.toList());
        return AssetsDto.builder().assets(assetsAmounts).build();
    }

    public void setAsset(AssetDto assetDto) {
        assetValidator.validate(assetDto);
        var assetEntity = assetsMapper.fromDtoToEntity(assetDto);
        assetsRepository.save(assetEntity);
    }
}
