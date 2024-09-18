package hello.upload.file;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;


    public String getFileDir(String fileName) {

        return fileDir + fileName;
    }


    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }

        return uploadFiles;
    }


    public UploadFile storeFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();

        int pos = originalFilename.lastIndexOf(".");

        String ext = originalFilename.substring(pos + 1);

        String uuid = UUID.randomUUID().toString();

        String storeFileName = uuid + "." + ext;

        file.transferTo(new File(getFileDir(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }
}
