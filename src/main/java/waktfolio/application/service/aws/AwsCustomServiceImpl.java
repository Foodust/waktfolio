package waktfolio.application.service.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import waktfolio.rest.dto.aws.DeleteAwsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsCustomServiceImpl implements AwsCustomService {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void deleteFile(HttpServletRequest request, DeleteAwsRequest deleteAwsRequest) {
        amazonS3Client.deleteObject(bucket, deleteAwsRequest.getFileName());
    }

    @Override
    public List<String> getFiles(UUID memberId) {
        List<String> list = new ArrayList<>();
        ObjectListing objectListing;
        objectListing = amazonS3Client.listObjects(bucket, memberId != null ? memberId.toString() : null);
        objectListing.getObjectSummaries().forEach(obj -> {
            list.add(obj.getKey());
        });
        return list;
    }
}
