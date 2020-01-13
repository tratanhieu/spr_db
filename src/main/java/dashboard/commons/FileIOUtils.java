package dashboard.commons;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class FileIOUtils {
    public static String createImageViaBase64Encode(String encodedString, String fileName) throws IOException {
        // Check if is image file
        String[] encodedArr = encodedString.split(",");
        String fileExtension = getImageFileExtendsion(encodedArr[0]);
        String encoded = encodedArr[1];
        if (fileExtension != null) {
            fileName += "." + fileExtension;
        } else {
            fileName += ".jpg";
        }
        // Get root path
        Path dir = FileSystems.getDefault().getPath("").toAbsolutePath();
        // Get date path. Ex: 2019/10/26
        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(DataUtils.getSystemDate());
        // Update file name
        String dirPath = String.format("%s/src/main/resources/static/%s", dir, "images/" + datePath + "/");
        String targetPath = String.format("%s/build/resources/main/static/%s", dir, "images/" + datePath + "/");
        File outputPath = new File(dirPath);
        if (!outputPath.exists()) {
            outputPath.mkdir();
        }
        // Get current path
        Path currentPath = FileSystems.getDefault().getPath(dirPath + "/" + fileName);
        // Convert from Base64 to Image
        outputPath = new File(String.valueOf(currentPath));
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        FileUtils.writeByteArrayToFile(outputPath, decodedBytes);
        // End Convert Base64 to Image

        return "/api/uploads/images/" + datePath + "/" + fileName;
    }

    private static String getImageFileExtendsion(String encodeImages) {
        switch (encodeImages) {
            case "image/png": return "png";
            case "image/jpg": return "jpg";
            case "image/jpeg": return "jpeg";
            default: return null;
        }
    }
}
