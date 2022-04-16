package az.fintech.unitech.repository;

import az.fintech.unitech.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByCif(Long cif);
    AccountEntity findByAccountNumber(Long accountNumber);
}
