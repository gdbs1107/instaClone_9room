package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class MemoCategoryHandler extends GeneralException {

    public MemoCategoryHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
