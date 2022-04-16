package az.fintech.unitech.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long cif;
    private Long accountNumber;
    private String currency;
    private String iban;
    private String bankSwift;
    private String bankCorAccount;
    private String bankName;
    private String taxId;
    private String bankAddress;
    private BigDecimal balance;
    private final String status = "OPEN";
    private final LocalDateTime accountCreateDate = LocalDateTime.now();
}
