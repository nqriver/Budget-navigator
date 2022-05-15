package pl.nqriver.homebudget.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repositories.AssetsRepository;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final UserLogInfoService userLogInfoService;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, UserLogInfoService userLogInfoService) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.userLogInfoService = userLogInfoService;
    }

    public List<AssetDto> getAllAssets() {
        LOGGER.debug("Get all assets");
        UserEntity loggedUser = getLoggedUser();
        return assetsRepository.getAssetEntitiesByUser(loggedUser)
                .stream()
                .map(assetsMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public AssetDto setAsset(AssetDto assetDto) {
        LOGGER.info("Set asset");
        LOGGER.debug("AssetDto: " + assetDto);
        UserEntity user = getLoggedUser();
        var assetEntity = assetsMapper.fromDtoToEntity(assetDto, user);
        LOGGER.info("Asset saved");

        AssetEntity savedEntity = assetsRepository.save(assetEntity);
        return assetsMapper.fromEntityToDto(savedEntity);
    }

    private UserEntity getLoggedUser() {
        LOGGER.info("Getting logged user");
        return userLogInfoService.getLoggedUserEntity();
    }

    public AssetDto updateAsset(AssetDto assetDto) {
        LOGGER.info("Update asset");
        LOGGER.debug("AssetDto" + assetDto);
        var optionalAssetEntity = assetsRepository.findById(assetDto.getId());
        if (optionalAssetEntity.isPresent()) {
            var entity = optionalAssetEntity.get();
            entity.setAmount(assetDto.getAmount());
            entity.setCategory(assetDto.getCategory());
            entity.setIncomeDate(assetDto.getIncomeDate());
            var updatedEntity = assetsRepository.saveAndFlush(entity);
            LOGGER.info("Asset updated");
            return assetsMapper.fromEntityToDto(updatedEntity);
        }
        throw new IllegalArgumentException("cannot find asset with the given id");
    }

    public List<AssetDto> getAssetsByCategory(AssetCategory category) {
        LOGGER.info("Getting assets by category");
        return assetsRepository.getAssetEntitiesByCategory(category)
                .stream()
                .map(assetsMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public void deleteAssetsByUser(UserEntity user) {
        assetsRepository.deleteAssetEntitiesByUser(user);
    }
}
