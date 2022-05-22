package com.example.top.plays.Repository;

import com.example.top.plays.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {

}
