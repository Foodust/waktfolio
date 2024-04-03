package waktfolio.backend.exception;

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
  public static BusinessException FILED_MEMBER_CREATE_MESSAGE_SEND(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_MATCHED_PASSWORD)
            .build();
  }
  public static BusinessException ALREADY_EXISTS_USER_ID(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.ALREADY_EXIST_USER_ID)
            .build();
  }
  public static BusinessException ALREADY_EXISTS_USER_ID(Integer number){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.ALREADY_EXIST_USER_ID)
            .number(number)
            .build();
  }
  public static BusinessException INVALID_TOKEN(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.INVALID_TOKEN)
            .build();
  }
  public static BusinessException NOT_FOUND_PROJECT(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_PROJECT)
            .build();
  }

  public static BusinessException NOT_FOUND_PERSONAL(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_PERSONAL)
            .build();
  }
  public static BusinessException NOT_FOUND_BUSINESS_REQUEST(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_BUSINESS_REQUEST)
            .build();
  }
  public static BusinessException NOT_FOUND_BUSINESS_REQUEST_ANSWER(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_BUSINESS_REQUEST_ANSWER)
            .build();
  }
  public static BusinessException NOT_COMMON_CODE(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_COMMON_CODE)
            .build();
  }
  public static BusinessException DOES_NOT_HAVE_PERMISSION(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.DOES_NOT_HAVE_PERMISSION)
            .build();
  }
  public static BusinessException DUPLICATE_COMMON_CODE_NAME(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.DUPLICATE_COMMON_CODE_NAME)
            .build();
  }
  public static BusinessException NOT_FOUND_MAN_MONTH_INFO(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.NOT_FOUND_MAN_MONTH_INFO)
            .build();
  }
  public static BusinessException DOES_NOT_MATCH_PASSWORD(){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.DOES_NOT_MATCH_PASSWORD)
            .build();
  }
  public static BusinessException DOES_NOT_MATCH_PASSWORD(Integer number){
    return BusinessException.builder()
            .applicationErrorCode(ApplicationErrorCode.DOES_NOT_MATCH_PASSWORD)
            .number(number)
            .build();
  }
}
