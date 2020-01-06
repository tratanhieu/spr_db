package dashboard.services;

import java.io.IOException;
import java.util.Map;

public interface ProvinceService {

    Map<String, String> listProvince() throws IOException;

    Map<String, String> listDistrict(String provinceId) throws IOException;

    Map<String, String> listWard(String provinceId, String districtId) throws IOException;

    Map<String, String> getAddress(String provinceId, String districtId, String wardId) throws IOException;

}
