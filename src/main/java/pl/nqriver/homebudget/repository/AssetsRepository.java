package pl.nqriver.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repository.entities.AssetEntity;

import java.util.List;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, Long> {
    List<AssetEntity> getAssetEntitiesByCategory(AssetCategory assetCategory);
}
