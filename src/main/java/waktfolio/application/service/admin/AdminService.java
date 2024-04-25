package waktfolio.application.service.admin;

import org.springframework.data.domain.Pageable;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;

public interface AdminService {
    List<FindContentResponse> getBeforeList(Pageable pageable);
}
