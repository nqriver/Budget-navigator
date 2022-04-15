package pl.nqriver.homebudget.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.service.dto.AssetDto;
import pl.nqriver.homebudget.validators.AssetValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, AssetValidator assetValidator) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assetValidator = assetValidator;
    }

    public List<AssetDto> getAllAssets() {
        LOGGER.debug("Get all assets");
        return assetsRepository.findAll()
                .stream()
                .map(assetsMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public void setAsset(AssetDto assetDto) {
        LOGGER.info("Set asset");
        LOGGER.debug("AssetDto: " + assetDto);
        assetValidator.validate(assetDto);
        var assetEntity = assetsMapper.fromDtoToEntity(assetDto);
        assetsRepository.save(assetEntity);
        LOGGER.info("Asset saved");
    }

    public void updateAsset(AssetDto assetDto) {
        LOGGER.info("Update asset");
        LOGGER.debug("AssetDto" + assetDto);
        var entity = assetsRepository.findById(assetDto.getId());
        entity.ifPresent(e -> {
            e.setAmount(assetDto.getAmount());
            assetsRepository.saveAndFlush(e);
        });
        LOGGER.info("Asset updated");
    }

    public List<AssetDto> getAssetsByCategory(AssetCategory category) {
        return assetsRepository.getAssetEntitiesByCategory(category)
                .stream()
                .map(assetsMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
