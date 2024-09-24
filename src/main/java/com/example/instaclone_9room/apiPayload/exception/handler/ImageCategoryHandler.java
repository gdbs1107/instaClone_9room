package com.example.instaclone_9room.apiPayload.exception.handler;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;

public class ImageCategoryHandler extends GeneralException {
    public ImageCategoryHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
