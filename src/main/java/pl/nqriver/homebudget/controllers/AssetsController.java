package pl.nqriver.homebudget.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import javax.validation.Valid;
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
    public ResponseEntity<AssetDto> setAsset(@RequestBody @Valid AssetDto assetDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetsService.setAsset(assetDto));
    }

    @PutMapping
    public ResponseEntity<AssetDto> updateAsset(@RequestBody @Valid AssetDto assetDto) {
        return ResponseEntity.ok().body(assetsService.updateAsset(assetDto));
    }

    @GetMapping("/find")
    public List<AssetDto> getAllAssetsByCategory(@PathParam("category") String category) {
        return assetsService.getAssetsByCategory(AssetCategory.valueOf(category.toUpperCase()));
    }
}
