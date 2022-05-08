package pl.nqriver.homebudget.controllers;

import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import javax.websocket.server.PathParam;
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

    @GetMapping("/find")
    public List<AssetDto> getAllAssetsByCategory(@PathParam("category") String category) {
        return assetsService.getAssetsByCategory(AssetCategory.valueOf(category.toUpperCase()));
    }
}
