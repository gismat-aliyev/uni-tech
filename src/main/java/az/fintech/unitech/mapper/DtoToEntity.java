package az.fintech.unitech.mapper;

import az.fintech.unitech.dto.request.AddAccountRequest;
import az.fintech.unitech.dto.request.PaymentRequest;
import az.fintech.unitech.dto.request.RegisterRequest;
import az.fintech.unitech.entity.AccountEntity;
import az.fintech.unitech.entity.CustomerEntity;
import az.fintech.unitech.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoToEntity {
    DtoToEntity INSTANCE = Mappers.getMapper(DtoToEntity.class);

    AccountEntity toEntity(AddAccountRequest request);

    static CustomerEntity toEntity(RegisterRequest request) {
        if (request == null) return null;
        CustomerEntity entity = new CustomerEntity();
        entity.setAddress(request.getDetails().getAddress());
        entity.setName(request.getDetails().getName());
        entity.setSurname(request.getDetails().getSurname());
        entity.setPin(request.getPin());
        entity.setPassword(request.getPassword());
        entity.setCif(request.getDetails().getCif());
        entity.setTaxId(request.getDetails().getTaxId());
        entity.setEmail(request.getDetails().getEmail());
        entity.setPhoneNumber(request.getDetails().getPhoneNumber());
        entity.setBirthDate(request.getDetails().getBirthDate());
        return entity;
    }

    PaymentEntity toEntity(PaymentRequest request);
}
