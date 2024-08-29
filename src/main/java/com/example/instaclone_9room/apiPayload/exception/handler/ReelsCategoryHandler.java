package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class ReelsCategoryHandler extends GeneralException {

    public ReelsCategoryHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
