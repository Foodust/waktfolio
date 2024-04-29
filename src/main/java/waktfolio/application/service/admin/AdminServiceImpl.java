package waktfolio.application.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.rest.dto.admin.ContentListRequest;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    @Override
    public List<FindContentResponse> getBeforeList(Pageable pageable) {
        List<Content> contents = contentRepository.findByUseYn(false, pageable);
        return contents.stream().map(contentMapper::findContentResponseOf).toList();
    }

    @Override
    public void updateContents(ContentListRequest contentListRequest) {
        List<Content> contents = contentRepository.findByIdIn(contentListRequest.getContentIds());
        contents.forEach(content -> {content.setUseYn(true);});
    }

    @Override
    public void deleteContents(ContentListRequest contentListRequest) {
        List<Content> contents = contentRepository.findByIdIn(contentListRequest.getContentIds());
        contentRepository.deleteAll(contents);
    }
}

