package hello.upload.controller;


import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/spring")
public class ServletUploadController {

    @Value("${file.dir}")
    private String filedir;


    @GetMapping("/upload")
    public String upload() {
        return "upload-form";
    }


    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file, HttpServletRequest request)
            throws ServletException, IOException {
        log.info("request= {}", request);
        log.info("file= {}", file);
        log.info("itemName= {}", itemName);

        if(!file.isEmpty()) {
            String fileFullPath = filedir + file.getOriginalFilename();
            log.info("fileFullPath= {}", fileFullPath);
            file.transferTo(new File(fileFullPath));
        }

        return "upload-form";
    }
}
