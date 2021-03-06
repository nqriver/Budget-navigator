package pl.nqriver.homebudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringExpenseRepository extends JpaRepository<RecurringExpenseEntity, Long> {
    List<RecurringExpenseEntity> findAllByUser(UserEntity loggedUserEntity);
    Optional<RecurringExpenseEntity> findByIdAndUser(Long id, UserEntity user);
}
