package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class FollowCategoryHandler extends GeneralException {
    public FollowCategoryHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
