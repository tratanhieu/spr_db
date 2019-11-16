package dashboard.commons;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FileIOUtils {
    public static String createImageViaBase64Encode(String encodedString, String fileName) throws IOException {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        String path = String.format("%s/src/main/resources/static/images/%s/%s/%s/%s",
            System.getProperty("user.dir"),
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
            fileName);

        File outputFile = new File(path);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
        return path;
    }
}
