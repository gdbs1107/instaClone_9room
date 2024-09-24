package com.example.instaclone_9room.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class S3Config {
    
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    
    @Value("${cloud.aws.region.static}")
    private String region;
    
    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return  AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .enablePathStyleAccess()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}