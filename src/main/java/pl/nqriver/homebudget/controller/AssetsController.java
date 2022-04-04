package pl.nqriver.homebudget.controller;

import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.service.AssetsService;
import pl.nqriver.homebudget.service.dto.AssetsDto;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    public AssetsController(AssetsService assetsService) {
        this.assetsService = assetsService;
    }


    @GetMapping
    public AssetsDto getAssets() {
        return assetsService.getAllAssets();
    }

    @PostMapping("/{asset}")
    public void setAsset(@PathVariable("asset") int asset) {
        assetsService.setAsset(asset);
    }
}
