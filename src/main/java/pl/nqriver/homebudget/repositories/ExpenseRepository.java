package pl.nqriver.homebudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> getExpenseEntityByUser(UserEntity user);

    List<ExpenseEntity> getAllByUserAndExpenseDateBetween(UserEntity user, LocalDateTime fromDate, LocalDateTime toDate);
}
