package waktfolio.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                        .status(400)
                        .timeStamp(LocalDateTime.now())
                .message(errorMessage)
                .build());
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleApplication(BusinessException ex, HttpServletRequest request) {
        String errorMessage = ex.getApplicationErrorCode().getMessage();
        ErrorResponse errorResponse;
        if(ex.getNumber() == null){
            errorResponse= ErrorResponse.builder()
                    .status(ex.getApplicationErrorCode().getStatus())
                    .timeStamp(LocalDateTime.now())
                    .message(errorMessage)
                    .build();
        }else{
            errorResponse = ErrorResponse.builder()
                    .status(ex.getApplicationErrorCode().getStatus())
                    .timeStamp(LocalDateTime.now())
                    .message(errorMessage)
                    .number(ex.getNumber())
                    .build();
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
