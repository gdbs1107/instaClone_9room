package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class TokenCategoryHandler extends GeneralException {
    public TokenCategoryHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
