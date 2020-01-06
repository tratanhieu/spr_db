package dashboard.services.implement;

import com.jayway.jsonpath.JsonPath;
import dashboard.services.ProvinceService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    final String NAME = "name_with_type";
    final String DISTRICT = "quan-huyen";
    final String WARD = "xa-phuong";

    private File jsonFile = new File("src/main/resources/province-list.json");

    private Map<String, Object> mapAll = new HashMap<>();

    private Map<String, String> mapProvince = new HashMap<>();

    @Override
    public Map<String, String> listProvince() throws IOException {

        if(mapAll == null || mapAll.size() != 63) {
            mapAll = JsonPath.read(jsonFile, "$");
            mapAll.forEach((k,v) -> mapProvince.put(k, (String) ((Map<String, Object>) mapAll.get(k)).get(NAME)));
        }

        return mapProvince;
    }

    @Override
    public Map<String, String> listDistrict(String provinceId) throws IOException{

        if(mapAll == null || mapAll.size() != 63) {
            listProvince();
        }

        final Map<String, Object> districtInfo = (Map<String, Object>) ((Map<String, Object>) mapAll.get(provinceId)).get(DISTRICT);
        Map<String, String> mapDistrict = new HashMap<>();

        districtInfo.forEach((k,v) -> mapDistrict.put(k, (String) ((Map<String, Object>) districtInfo.get(k)).get(NAME)));

        return mapDistrict;
    }

    @Override
    public Map<String, String> listWard(String provinceId, String districtId) throws IOException {

        if(mapAll == null || mapAll.size() != 63) {
            listProvince();
        }

        final Map<String, Object> wardInfo = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>)
                ((Map<String, Object>) mapAll.get(provinceId)).get(DISTRICT)).get(districtId)).get(WARD);
        Map<String, String> mapWard = new HashMap<>();

        wardInfo.forEach((k,v) -> mapWard.put(k, (String) ((Map<String, Object>) wardInfo.get(k)).get(NAME)));

        return mapWard;
    }

    @Override
    public Map<String ,String> getAddress(String provinceId, String districtId, String wardId) throws IOException {

        if(mapAll == null || mapAll.size() != 63) {
            listProvince();
        }

        Map<String, String> address = new HashMap<>();

        address.put("province", listProvince().get(provinceId));
        address.put("district", listDistrict(provinceId).get(districtId));
        address.put("ward", listWard(provinceId, districtId).get(wardId));

        return address;
    }
}
