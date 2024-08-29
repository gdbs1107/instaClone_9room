package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class MemberCategoryHandler extends GeneralException {

    public MemberCategoryHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
