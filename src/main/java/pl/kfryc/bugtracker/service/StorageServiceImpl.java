package pl.kfryc.bugtracker.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.config.StorageProperties;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.exception.StorageException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@Service
public class StorageServiceImpl implements StorageService {

    private Path rootLocation;

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointURL}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;


    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {

        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, int ticketId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            } else if (fileName.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory" + fileName);
            } else {
                uploadFileToS3bucket(ticketId + "/" + fileName, convertMultiPartToFile(file));
            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName);
        }

        return fileName;
    }

    @Override
    public String storeProfilePic(MultipartFile file, User user) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }

        // To avoid clashing of possible names
        String hash = Math.abs(user.getEmail().hashCode()) + extension;
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory" + fileName);
            }
            if (user.getProfilePic().equals(hash)) {
                uploadFileToS3bucket("profilePic/" + hash, convertMultiPartToFile(file));
                //Files.copy(inputStream, this.rootLocation.resolve("profilePic\\" + hash), StandardCopyOption.REPLACE_EXISTING);
            } else {
                s3client.deleteObject(new DeleteObjectRequest(bucketName, "profilePic/" + hash));
                uploadFileToS3bucket("profilePic/" + hash, convertMultiPartToFile(file));
                //Files.delete(this.rootLocation.resolve("profilePic\\" + user.getProfilePic()));
                //Files.copy(inputStream, this.rootLocation.resolve("profilePic\\" + hash), StandardCopyOption.REPLACE_EXISTING);
            }


        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName);
        }

        return hash;
    }

    @Override
    public byte[] load(String dir, String filename) throws IOException {
        try {
            s3client.getObject(bucketName, dir + "/" + filename);
            InputStream in = s3client.getObject(bucketName, dir + "/" + filename).getObjectContent();

            return IOUtils.toByteArray(in);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to load image");
        }
    }

    // private function
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileToS3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

}
