package az.fintech.unitech.repository;

import az.fintech.unitech.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByPinAndPassword(String pin, String password);
    Optional<CustomerEntity> findByPin(String pin);
    List<CustomerEntity> findByStatus(int status);
}
