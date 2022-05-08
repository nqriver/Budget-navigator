package pl.nqriver.homebudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;

import java.util.List;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, Long> {
    List<AssetEntity> getAssetEntitiesByCategory(AssetCategory assetCategory);

    @Query("SELECT a FROM AssetEntity a WHERE a.user = :user")
    List<AssetEntity> getAssetEntitiesByUser(UserEntity user);

    void deleteAssetEntitiesByUser(UserEntity userEntity);
}
