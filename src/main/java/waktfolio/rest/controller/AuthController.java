package waktfolio.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest){

        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }


}
