package waktfolio.application.service.aws;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.aws.DeleteAwsRequest;

import java.util.List;
import java.util.UUID;

public interface AwsCustomService {
    void deleteFile(HttpServletRequest request, DeleteAwsRequest deleteAwsRequest);
    List<String> getFiles(UUID memberId);
}
