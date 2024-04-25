package waktfolio.rest.dto.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAwsRequest {
    private String fileName;
}
