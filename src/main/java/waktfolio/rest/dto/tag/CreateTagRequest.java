package waktfolio.rest.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagRequest {
    @Length(min = 1,max = 20)
    private String tagName;
}
