package pl.nqriver.homebudget.controller;

import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.service.AssetsService;
import pl.nqriver.homebudget.service.dto.AssetDto;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    public AssetsController(AssetsService assetsService) {
        this.assetsService = assetsService;
    }


    @GetMapping
    public List<AssetDto> getAssets() {
        return assetsService.getAllAssets();
    }

    @PostMapping
    public void setAsset(@RequestBody AssetDto assetDto) {
        assetsService.setAsset(assetDto);
    }

    @PutMapping
    public void updateAsset(@RequestBody AssetDto assetDto) {
        assetsService.updateAsset(assetDto);
    }
}
