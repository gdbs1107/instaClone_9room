package com.example.instaclone_9room.apiPayload.code.status;


import com.example.instaclone_9room.apiPayload.code.BaseCode;
import com.example.instaclone_9room.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다.");
    //enum SuccessStatus라는 열거형의 하나의 상수
    //_OK상수를 선언하고 SuccessStatus 생성자를 호출하여 HttpStatus, code, message 필드를 초기화하는 구문

    // 멤버 관련 응답

    // ~~~ 관련 응답

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
