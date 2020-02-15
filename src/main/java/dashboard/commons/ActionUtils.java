package dashboard.commons;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ActionUtils {
    public static Pageable preparePageable(String sort, Integer page, Integer size) {
        String[] splitData = sort.split(",");
        Sort sortable = Sort.by("createDate").ascending();
        if (splitData.length == 2) {
            sortable = splitData[1].equals("ASC") ? Sort.by(splitData[0]).ascending() : Sort.by(splitData[0]).descending();
        }
        page = 1 >= page ? 0 : (page - 1);
        return PageRequest.of(page, size, sortable);
    }

    public static String hashPassWordMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String genOtp() {
        Random rnd = new Random();
        int otp = 100000 + rnd.nextInt(900000);

        return String.valueOf(otp);
    }
}
