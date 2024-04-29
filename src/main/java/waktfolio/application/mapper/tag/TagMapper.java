package waktfolio.application.mapper.tag;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Tag;
import waktfolio.rest.dto.tag.CreateTagRequest;
import waktfolio.rest.dto.tag.FindTagResponse;
import waktfolio.rest.dto.tag.UpdateTagRequest;

import java.util.Optional;
import java.util.UUID;

@Component
public class TagMapper {

    public Tag tagOf(UUID memberId, CreateTagRequest createTagRequest){
        return Tag.builder()
                .memberId(memberId)
                .name(createTagRequest.getTagName())
                .build();
    }
    public FindTagResponse findTagResponseFrom(Tag tag){
        return FindTagResponse.builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .build();
    }
    public void updateTag(Tag tag, UpdateTagRequest updateTagRequest){
        Optional.ofNullable(updateTagRequest.getTagName()).ifPresent(tag::setName);
    }
}
