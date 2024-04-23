package waktfolio.rest.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTagRequest {
    @NotNull
    @NotEmpty
    private UUID tagId;
    @Length(min = 1,max = 20)
    private String tagName;
}
