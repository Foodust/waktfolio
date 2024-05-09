package waktfolio.application.service.admin;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.rest.dto.admin.ContentListRequest;
import waktfolio.rest.dto.content.FindContentDetail;
import waktfolio.rest.dto.content.FindContentDetailResponse;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<FindContentDetailResponse> getBeforeList(Pageable pageable) {
        List<FindContentDetail> contents = contentRepository.findByUseYn(false, pageable);
        return contents.stream().map(contentMapper::findContentDetailResponseOf).toList();
    }

    @Override
    public void updateContents(ContentListRequest contentListRequest) {
        List<Content> contents = contentRepository.findByIdIn(contentListRequest.getContentIds());
        contents.forEach(content -> {
            content.setUseYn(true);
        });
    }

    @Override
    public void deleteContents(ContentListRequest contentListRequest) {
        List<Content> contents = contentRepository.findByIdIn(contentListRequest.getContentIds());
        contents.forEach(content -> {
            deleteAwsFile(content.getObjectPath());
            Optional.ofNullable(content.getBackGroundPath()).ifPresent(this::deleteAwsFile);
            Optional.ofNullable(content.getThumbnailImagePath()).ifPresent(this::deleteAwsFile);
        });
        contentRepository.deleteAll(contents);
    }

    private void deleteAwsFile(String path) {
        amazonS3Client.deleteObject(bucket, path);
    }
}

