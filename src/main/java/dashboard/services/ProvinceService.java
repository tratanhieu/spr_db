package dashboard.services;

import java.io.IOException;
import java.util.Map;

public interface ProvinceService {

    Map<String, String> listProvince();

    Map<String, String> listDistrict(String provinceId) throws IOException;

    Map<String, String> listWard(String provinceId, String districtId) throws IOException;

    String getProvince(String provinceId) throws IOException;

    String getDistrict(String provinceId, String districtId) throws IOException;

    String getWard(String provinceId, String districtId, String wardId) throws IOException;
}
