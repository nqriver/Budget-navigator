package pl.nqriver.homebudget.service;


import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.service.dto.AssetsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AssetsService {

    private AssetsDto assetsDto = new AssetsDto();

    public AssetsDto getAllAssets() {
        return assetsDto;
    }

    public void setAsset(int asset) {
        var allAssets = assetsDto.getAssets();
        if (allAssets == null) {
            allAssets = new ArrayList<>();
        }
        allAssets.add(asset);
        assetsDto.setAssets(allAssets);
    }
}
