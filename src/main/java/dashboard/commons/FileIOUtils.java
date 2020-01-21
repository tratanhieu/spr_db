package dashboard.commons;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIOUtils {
    private static final Pattern IMG_PATTERN = Pattern.compile(
            "<img(\\s+.*?)(?:src\\s*=\\s*(?:'|\")(.*?)(?:'|\"))(.*?)/>",
            Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static final String IP = "http://localhost:5000";

    public static String createImageViaBase64Encode(String encodedString, String fileName) throws IOException {
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

        return IP + "/api/uploads/images/" + datePath + "/" + fileName;
    }

    public static String prepareContentPost(String content, String slugName) throws IOException {
        StringBuilder sb = new StringBuilder(content);
        Matcher matcherImgTags = IMG_PATTERN.matcher(sb.toString());
        int start = 0, end = 0;
        int startUrl = 0, endUrl = 0;
        int index = 0;
        StringBuilder builder = new StringBuilder();
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
                String url = createImageViaBase64Encode(image, slugName + "-" +  index);
                builder = new StringBuilder(replaceUrlStr);
                builder.replace(startUrl, endUrl, "src=\"" + url + "\"");
                index++;
            }
            sb.replace(start, end, builder.toString());
        }
        return sb.toString();
    }
}
