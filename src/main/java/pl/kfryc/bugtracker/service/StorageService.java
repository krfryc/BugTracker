package pl.kfryc.bugtracker.service;

import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.entity.User;

import java.io.IOException;

public interface StorageService {

    void init();

    String store(MultipartFile file, int ticketId);

    String storeProfilePic(MultipartFile file, User user);

    byte[] load(String dir, String filename) throws IOException;

}
