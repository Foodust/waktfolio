package waktfolio.application.service.admin;

import org.springframework.data.domain.Pageable;
import waktfolio.rest.dto.admin.ContentListRequest;
import waktfolio.rest.dto.content.FindContentDetailResponse;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;

public interface AdminService {
    List<FindContentDetailResponse> getBeforeList(Pageable pageable);
    void updateContents(ContentListRequest contentListRequest);
    void deleteContents(ContentListRequest contentListRequest);
}
