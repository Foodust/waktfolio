package waktfolio.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {


    @Builder.Default
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;
    private Integer number;

    public static ErrorResponse of(HttpServletRequest request, ApplicationErrorCode errorCode, String message){
        return ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(errorCode.getStatus())
                .error(errorCode.getMessage())
                .message(message)
                .path(request.getServletPath())
                .build();
    }
    public static ErrorResponse of(HttpServletRequest request, ApplicationErrorCode errorCode, BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }
        return of(request, errorCode, builder.toString());
    }
}

