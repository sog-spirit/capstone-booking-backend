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
    public static final String FOLDER_SEPERATOR = "\\";

    public static void writeFile(MultipartFile multipartFile, Long fileId, String baseFolder, String fileName) throws IOException {
        File file = new File(getImageFilePath(fileId, baseFolder, fileName));
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (OutputStream outputStream = new FileOutputStream(file, false)) {
            outputStream.write(multipartFile.getBytes());
        }
    }

    public static String getImageFilePath(Long fileId, String baseFolder, String fileName) {
        return new StringBuffer()
                .append(ROOT_FILE_PATH)
                .append(FOLDER_SEPERATOR)
                .append(baseFolder)
                .append(FOLDER_SEPERATOR)
                .append(fileId)
                .append(FOLDER_SEPERATOR)
                .append(fileName)
                .toString();
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public static String getRootFilePath() {
        return ROOT_FILE_PATH;
    }
    @Value("${ROOT_CONTENT_PATH}")
    public void setRootFilePath(String rootFilePath) {
        ROOT_FILE_PATH = rootFilePath;
    }
}
