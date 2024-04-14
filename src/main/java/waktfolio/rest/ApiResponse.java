package waktfolio.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();
    private int status;
    private Object data;
    private String path;
    private ApiResponse(LocalDateTime timeStamp, int status, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.path = path;
    }
    public static ApiResponse of(HttpServletRequest request, Object data) {
        return new ApiResponse(LocalDateTime.now(), HttpStatus.OK.value(), data, request.getServletPath());
    }
    public static ApiResponse of(HttpServletRequest request) {
        return new ApiResponse(LocalDateTime.now(), HttpStatus.OK.value(), request.getServletPath());
    }
    public static ApiResponse of(HttpServletRequest request, HttpStatus status) {
        return new ApiResponse(LocalDateTime.now(), status.value(), request.getServletPath());
    }
    public static ApiResponse of(HttpServletRequest request, HttpStatus status, Object data) {
        return new ApiResponse(LocalDateTime.now(), status.value(), data, request.getServletPath());
    }
}
