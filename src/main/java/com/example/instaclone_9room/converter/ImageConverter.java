package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.controller.dto.postDTO.ImageDTO;
import com.example.instaclone_9room.domain.feedEntity.Image;

import java.util.List;
import java.util.stream.Collectors;

public class ImageConverter {

    public static Image toImage(ImageDTO imageDTO) {
        
        return Image.builder()
                .fileName(imageDTO.getFileName())
                .imagePath(imageDTO.getImagePath())
                .build();
    }
    
    public static List<Image> toImageList(List<ImageDTO> imageDTOList) {
        
        return imageDTOList.stream()
                .map(ImageConverter::toImage)
                .collect(Collectors.toList());
    }
}
