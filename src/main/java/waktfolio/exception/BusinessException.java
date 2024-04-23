package waktfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessException extends RuntimeException{
  private ApplicationErrorCode applicationErrorCode;
  private Integer number;
  public BusinessException(ApplicationErrorCode applicationErrorCode) {
    super(applicationErrorCode.getMessage());
    this.applicationErrorCode = applicationErrorCode;
  }
  public BusinessException(String message, ApplicationErrorCode applicationErrorCode) {
    super(message);
    this.applicationErrorCode = applicationErrorCode;
  }
  public static BusinessException NOT_FOUND_MEMBER(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_MEMBER)
            .build();
  }
  public static BusinessException NOT_MATCHED_PASSWORD(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_MATCHED_PASSWORD)
            .build();
  }
  public static BusinessException ALREADY_EXISTS_USER_ID(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.ALREADY_EXIST_USER_ID)
            .build();
  }
  public static BusinessException NOT_FOUND_CONTENT(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_CONTENT)
            .build();
  }

  public static BusinessException NOT_FOUND_TAG(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_TAG)
            .build();
  }
  public static BusinessException ALREADY_EXITS_TAG_NAME(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.ALREADY_EXITS_TAG_NAME)
            .build();
  }

}
