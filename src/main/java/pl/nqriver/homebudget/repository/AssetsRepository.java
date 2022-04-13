package pl.nqriver.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.repository.entities.AssetEntity;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, Long> {
}
