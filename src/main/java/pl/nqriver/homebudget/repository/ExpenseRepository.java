package pl.nqriver.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nqriver.homebudget.repository.entities.ExpenseEntity;
import pl.nqriver.homebudget.repository.entities.UserEntity;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> getExpenseEntityByUser(UserEntity user);
}
