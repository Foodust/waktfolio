package waktfolio.application.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    @Override
    public List<FindContentResponse> getBeforeList(Pageable pageable) {
        List<Content> contents = contentRepository.findByUseYn(false, pageable);
        return contents.stream().map(contentMapper::findContentResponseOf).toList();
    }
}

