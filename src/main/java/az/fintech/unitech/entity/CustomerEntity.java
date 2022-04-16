package az.fintech.unitech.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String pin;
    private String password;
    private Long cif;
    private String taxId;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate birthDate;
    private final int status = 1;
    private final LocalDateTime createDate = LocalDateTime.now();

}
