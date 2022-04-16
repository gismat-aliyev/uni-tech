package az.fintech.unitech.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonErrorResponse {
    private int errorCode;
    private String errorMessage;
}
