package waktfolio.application.service.tag;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.application.mapper.tag.TagMapper;
import waktfolio.domain.entity.content.Tag;
import waktfolio.domain.repository.tag.TagRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.tag.CreateTagRequest;
import waktfolio.rest.dto.tag.FindTagResponse;
import waktfolio.rest.dto.tag.UpdateTagRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final JwtTokenUtil jwtTokenUtil;
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;
    @Override
    public void createTag(HttpServletRequest request, CreateTagRequest createTagRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        tagRepository.findByMemberIdAndName(memberId,createTagRequest.getTagName()).ifPresent(a -> {throw BusinessException.ALREADY_EXITS_TAG_NAME();});
        Tag tag = tagMapper.tagOf(memberId, createTagRequest);
        tagRepository.save(tag);
    }

    @Override
    public void deleteTag(HttpServletRequest request, UUID tagId) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Tag tag = tagRepository.findByIdAndMemberId(tagId, memberId).orElseThrow(BusinessException::NOT_FOUND_TAG);
        tagRepository.delete(tag);
    }

    @Override
    public void updateTag(HttpServletRequest request, UpdateTagRequest updateTagRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Tag tag = tagRepository.findByIdAndMemberId(updateTagRequest.getTagId(), memberId).orElseThrow(BusinessException::NOT_FOUND_TAG);
        tagMapper.updateTag(tag,updateTagRequest);
    }

    @Override
    public List<FindTagResponse> findTag(HttpServletRequest request) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        List<Tag> tags = tagRepository.findByMemberIdOrderByName(memberId);
        return tags.stream().map(tagMapper::findTagResponseFrom).toList();
    }
}
