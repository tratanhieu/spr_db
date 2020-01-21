package dashboard.commons;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIOUtils {
    private static final Pattern IMG_PATTERN = Pattern.compile(
            "<img[^>]+src=\"data:image\\/([^\">]+)\"",
            Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static final String IP = "http://localhost:5000";
    private static final String IMAGE_FOLDER = "/api/uploads/images/";
    public static final String PATH = "PATH";
    private static final String SYSTEM_PATH = "SYSTEM_PATH";
    private static final String STATIC_FOLDER = "%s/src/main/resources/static/%s";

    private List<File> uploadedFiles;

    public FileIOUtils() {
        uploadedFiles = new ArrayList<>();
    }

    public Map createImageViaBase64Encode(String encodedString, String fileName) throws IOException {
        // Check if is image file
        String[] encodedArr = encodedString.split(",");
        String fileExtension = "jpg";
        String encoded = encodedString;
        if (encodedArr.length == 2) {
            encoded = encodedArr[1];
        }
        fileName += "." + fileExtension;
        // Get root path
        Path dir = FileSystems.getDefault().getPath("").toAbsolutePath();
        // Get date path. Ex: 2019/10/26
        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(DataUtils.getSystemDate());
        // Update file name
        String dirPath = String.format(STATIC_FOLDER, dir, "images/" + datePath + "/");
        File outputPath = new File(dirPath);
        if (!outputPath.exists() && !outputPath.mkdir()) {
            throw new DirectoryNotEmptyException("Can create Folder");
        }
        // Get current path
        Path currentPath = FileSystems.getDefault().getPath(dirPath + "/" + fileName);
        // Convert from Base64 to Image
        outputPath = new File(String.valueOf(currentPath));
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        FileUtils.writeByteArrayToFile(outputPath, decodedBytes);
        // End Convert Base64 to Image
        Map<String, Object> map = new HashMap<>();
        map.put(PATH, IP + IMAGE_FOLDER + datePath + "/" + fileName);
        map.put(SYSTEM_PATH, outputPath);
        return map;
    }

    public void rollBackUploadedImages() {
        if (this.uploadedFiles.size() > 0) {
            this.uploadedFiles.forEach(FileUtils::deleteQuietly);
        }
    }

    public String prepareContentPost(String content, String slugName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder(content);
        try {
            StringBuilder builder = new StringBuilder();
            Matcher matcherImgTags = IMG_PATTERN.matcher(contentBuilder.toString());
            int start = 0, end = 0;
            int startUrl = 0, endUrl = 0;
            int index = 0;
            while (matcherImgTags.find()) { // find next match
                start = matcherImgTags.start();
                end = matcherImgTags.end();
                String replaceUrlStr = matcherImgTags.group();
                Pattern pattern = Pattern.compile("src=\"(.*?)\"");
                Matcher matcherImgUrl = pattern.matcher(replaceUrlStr);
                if (matcherImgUrl.find()) {
                    String image = matcherImgUrl.group(1);
                    startUrl = matcherImgUrl.start();
                    endUrl = matcherImgUrl.end();
                    // Upload
                    Map resultMap = createImageViaBase64Encode(image, slugName + "-" + index);
                    String url = (String) resultMap.get(PATH);
                    File file = (File) resultMap.get(SYSTEM_PATH);
                    this.uploadedFiles.add(file);
                    builder = new StringBuilder(replaceUrlStr);
                    builder.replace(startUrl, endUrl, "src=\"" + url + "\"");
                    index++;
                }
                contentBuilder.replace(start, end, builder.toString());
                matcherImgTags = IMG_PATTERN.matcher(contentBuilder.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.rollBackUploadedImages();
        }
        return contentBuilder.toString();
    }
}
