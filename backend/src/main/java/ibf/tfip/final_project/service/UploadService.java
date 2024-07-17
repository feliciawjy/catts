package ibf.tfip.final_project.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService {

    @Autowired
    private AmazonS3 s3;


    public String uploadFile(MultipartFile file) throws IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("upload-timestamp", new Date().toString());
        userData.put("original-filename", file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0, 8);
        PutObjectRequest putReq = new PutObjectRequest("fel", key, file.getInputStream(), metadata);
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(putReq);

        return s3.getUrl("fel", key).toString();
    }
}