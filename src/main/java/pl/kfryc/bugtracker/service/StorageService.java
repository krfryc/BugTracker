package pl.kfryc.bugtracker.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.entity.User;

import java.nio.file.Path;

public interface StorageService {

    void init();

    String store(MultipartFile file, int ticketId);

    String storeProfilePic(MultipartFile file, User user);

    Path load(String filename);

    Resource loadImage(String filename);

}
