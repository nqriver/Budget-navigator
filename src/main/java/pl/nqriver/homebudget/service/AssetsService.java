package pl.nqriver.homebudget.service;


import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.repository.AssetsRepository;
import pl.nqriver.homebudget.service.dto.AssetDto;
import pl.nqriver.homebudget.validators.AssetValidator;

import java.util.List;
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

    public List<AssetDto> getAllAssets() {
        return assetsRepository.findAll()
                .stream()
                .map(assetsMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public void setAsset(AssetDto assetDto) {
        assetValidator.validate(assetDto);
        var assetEntity = assetsMapper.fromDtoToEntity(assetDto);
        assetsRepository.save(assetEntity);
    }

    public void updateAsset(AssetDto assetDto) {
        var entity = assetsRepository.findById(assetDto.getId());
        entity.ifPresent( e -> {
            e.setAmount(assetDto.getAmount());
            assetsRepository.saveAndFlush(e);
        });
    }
}
