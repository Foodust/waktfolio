package waktfolio.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BaseListDto<T> {
    private Integer totalPage;
    private List<T> list;
}
