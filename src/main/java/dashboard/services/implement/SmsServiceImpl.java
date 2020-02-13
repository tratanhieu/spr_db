package dashboard.services.implement;

import dashboard.services.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class SmsServiceImpl implements SmsService {

    private static final String apiKey = "0110D394C820A5940E79DA5A17B938";

    private static final String secretKey = "F965DB51B369D9048B6184ACBD980E";

    private static final String content = "Ma xac thuc dang ky spr_db cua ban la: ";

    private static final String smsType = "8";

    private static final String brandName = "";

    private static final String sandBox = "1";

    private static final String uri = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_post_json/";

    @Override
    public Object sendSms(String phone, String otpCode) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> map = new HashMap<>();
        map.put("ApiKey", apiKey + 1);
        map.put("SecretKey", secretKey);
        map.put("Content", content + otpCode);
        map.put("Phone", phone);
//        map.put("Brandname", brandName);
        map.put("SmsType", smsType);

        Object result = restTemplate.postForObject(uri, map, Map.class);

        return result;
    }
}
