package com.example.instaclone_9room.apiPayload.code.status;


import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {


    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500","서버에러, 관리자에게 문의 바랍니다"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    //토큰 관련 에러
    TOKEN_NOT_INCORRECT(HttpStatus.UNAUTHORIZED,"TOKEN2001","유효하지 않은 토큰입니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"TOKEN2002","만료된 토큰입니다"),
    TOKEN_NULL(HttpStatus.UNAUTHORIZED,"TOKEN2003","토큰이 존재하지 않습니다"),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER3001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER3002", "닉네임은 필수 입니다."),
    USERNAME_EXISTED(HttpStatus.BAD_REQUEST, "MEMBER3003", "이미 존재하는 아이디입니다"),
    TOO_LONG_REQUEST(HttpStatus.BAD_REQUEST,"MEMBER3004","최대 20자입니다"),
    GENDER_ERROR(HttpStatus.BAD_REQUEST,"MEMBER3005","성별을 제대로 입력해주세요"),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "MEMBER3006","본인의 계정이 아닙니다"),

    //릴스 관련 에러
    REELS_NOT_FOUND(HttpStatus.NOT_FOUND, "REELS4001", "릴스가 없습니다."),
    REELS_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REELS4002", "릴스댓글이 없습니다."),

    //팔로우, 팔로워 관련 에러
    NOT_YOUR_FOLLOWER(HttpStatus.BAD_REQUEST,"FOLLOW5001","팔로우가 되어있지 않아 요청을 처리 할 수 없습니다"),
    TARGET_NOT_FOUND(HttpStatus.NOT_FOUND,"FOLLOW5002","대상을 찾을 수 없습니다");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
