package waktfolio.rest.controller.aws;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.aws.AwsCustomService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.aws.DeleteAwsRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/aws")
@RequiredArgsConstructor
public class AwsController {
    private final AwsCustomService awsCustomService;
    @PostMapping("")
    public ResponseEntity<ApiResponse> deleteFile(HttpServletRequest request, @RequestBody DeleteAwsRequest deleteAwsRequest) {
        awsCustomService.deleteFile(request,deleteAwsRequest);
        return new ResponseEntity<>(ApiResponse.of(request), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse> getFolder(HttpServletRequest request, UUID memberId) {
        List<String> files = awsCustomService.getFiles(memberId);
        return new ResponseEntity<>(ApiResponse.of(request,files), HttpStatus.OK);
    }
}
