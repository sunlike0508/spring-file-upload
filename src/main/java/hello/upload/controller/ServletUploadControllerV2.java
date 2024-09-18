package hello.upload.controller;


import java.io.IOException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String filedir;


    @GetMapping("/upload")
    public String upload() {
        return "upload-form";
    }


    @PostMapping("/upload")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        Collection<Part> parts = request.getParts();

        log.info("parts = {}", parts);

        for(Part part : parts) {
            log.info("=== Part ===");
            log.info("name = {}", part.getName());

            Collection<String> headerNames = part.getHeaderNames();

            for(String headerName : headerNames) {
                log.info("headerName {} : {}", headerName, part.getHeader(headerName));
            }

            log.info("submitted = {}", part.getSubmittedFileName());
            log.info("size = {}", part.getSize());

            log.info("contentType = {}", part.getContentType());

            //            InputStream inputStream = part.getInputStream();
            //            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            //
            //            log.info("body = {}", body);

            if(StringUtils.hasText(part.getSubmittedFileName())) {
                String filePath = filedir + part.getSubmittedFileName();
                log.info("filePath = {}", filePath);
                part.write(filePath);
            }
        }

        return "upload-form";
    }
}
