package online.ptsports.PTSports.Service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface FileUploadCloudinary {
    String uploadFile(MultipartFile multipartFile) throws IOException;
    void deleteImage(String imageUrl);
}
