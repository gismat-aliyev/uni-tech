package az.fintech.unitech.mapper;

import az.fintech.unitech.dto.response.AccountResponse;
import az.fintech.unitech.dto.response.AddAccountResponse;
import az.fintech.unitech.dto.response.CustomerResponse;
import az.fintech.unitech.entity.AccountEntity;
import az.fintech.unitech.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityToDto {
    EntityToDto INSTANCE = Mappers.getMapper(EntityToDto.class);

    CustomerResponse toDto(CustomerEntity entity);

    AccountResponse toDto(AccountEntity entity);

    AddAccountResponse toAddDto(AccountEntity entity);
}
