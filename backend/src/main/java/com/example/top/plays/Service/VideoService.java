package com.example.top.plays.Service;

import com.example.top.plays.Repository.VideoRepository;
import com.example.top.plays.dto.UploadVideoResponse;
import com.example.top.plays.dto.VideoDto;
import com.example.top.plays.model.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile){
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        var saveVideo =videoRepository.save(video);

        return new UploadVideoResponse(saveVideo.getId(), saveVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {

        //Find the video by videoID
        var savedVideo = getVideoById(videoDto.getId());
        //Map the videoDto fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());
        //save the video to the db
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoID) {
        var saveVideo = getVideoById(videoID);

       String thumbnailUrl =  s3Service.uploadFile(file);
       saveVideo.setThumbnailUrl(thumbnailUrl);
       videoRepository.save(saveVideo);
       return thumbnailUrl;

    }

    Video getVideoById(String videoId){
       return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id -" + videoId));
    }
}
