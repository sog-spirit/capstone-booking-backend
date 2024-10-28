package com.capstone.core.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource("classpath:env.properties")
public class FileUtils {
    private static String ROOT_FILE_PATH;
    private static final String PHOTO_FILE_EXTENSION = ".jpg";

    public static void writeFile(MultipartFile multipartFile, Long fileId, String baseFolder) throws IOException {
        File file = new File(ROOT_FILE_PATH + "\\" + baseFolder + "\\" + fileId + "\\" + fileId + PHOTO_FILE_EXTENSION);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        }
    }

    public static String getRootFilePath() {
        return ROOT_FILE_PATH;
    }
    @Value("${ROOT_CONTENT_PATH}")
    public void setRootFilePath(String rootFilePath) {
        ROOT_FILE_PATH = rootFilePath;
    }
}
