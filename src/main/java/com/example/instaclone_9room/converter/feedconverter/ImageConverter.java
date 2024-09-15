package com.example.instaclone_9room.converter.feedconverter;

import com.example.instaclone_9room.controller.dto.feedDTO.ImageDTO;
import com.example.instaclone_9room.domain.feedEntity.Image;

import java.util.List;
import java.util.stream.Collectors;

public class ImageConverter {
    
    public static Image toImage(ImageDTO.ImageRequestDTO imageDTO) {
        
        return Image.builder()
                .fileName(imageDTO.getFileName())
                .imagePath(imageDTO.getImagePath())
                .build();
    }
    
    public static List<Image> toImageList(List<ImageDTO.ImageRequestDTO> imageListDTO) {
        
        return imageListDTO.stream()
                .map(image -> Image.builder()
                        .fileName(image.getFileName())
                        .imagePath(image.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }
    
    public static ImageDTO.ImageResponseDTO toImageDTO(Image image) {
        return ImageDTO.ImageResponseDTO.builder()
                .imagePath(image.getImagePath())
                .fileName(image.getFileName())
                .build();
    }
    
    public static List<ImageDTO.ImageResponseDTO> toImageDTOList(List<Image> imageList) {
        return imageList.stream()
                .map(image -> ImageDTO.ImageResponseDTO.builder()
                        .fileName(image.getFileName())
                        .imagePath(image.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }
    
}