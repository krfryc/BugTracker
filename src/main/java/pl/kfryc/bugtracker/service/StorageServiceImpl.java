package pl.kfryc.bugtracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.exception.StorageException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@PropertySource("${classpath*:application.properties}")
public class StorageServiceImpl implements StorageService {

    @Value("${storage.location}")
    private Path rootLocation;

    @Override
    @PostConstruct
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        } catch (IOException e){
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, int ticketId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if(fileName.contains("..")){
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory" + fileName);
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, this.rootLocation.resolve(ticketId+"\\"+fileName), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e){
            throw new StorageException("Failed to store file " + fileName);
        }

        return fileName;
    }

    @Override
    public String storeProfilePic(MultipartFile file, User user) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension="";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }

        // To avoid clashing of possible names id (user email as unique variable) will be hashed
        String hash = Math.abs(user.getEmail().hashCode())+extension;
        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if(fileName.contains("..")){
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory" + fileName);
            }
            try(InputStream inputStream = file.getInputStream()){
                if(user.getProfilePic().equals(hash)){
                    Files.copy(inputStream, this.rootLocation.resolve("profilePic\\"+hash), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.delete(this.rootLocation.resolve("profilePic\\"+user.getProfilePic()));
                    Files.copy(inputStream, this.rootLocation.resolve("profilePic\\"+hash), StandardCopyOption.REPLACE_EXISTING);
                }

            }
        } catch (IOException e){
            throw new StorageException("Failed to store file " + fileName);
        }

        return hash;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadImage(String filename) {
        try{
            Path file = rootLocation.resolve("profilePic\\"+filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Could not find the image");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Failed to load image");
        }
    }
}
