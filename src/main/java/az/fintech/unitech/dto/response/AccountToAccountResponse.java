package az.fintech.unitech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountToAccountResponse {
    private String response;
    private Long paymentId;
    private CommonErrorResponse errorResponse;
}
