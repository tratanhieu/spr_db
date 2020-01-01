package dashboard.controllers.province;

import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/province", produces = "application/json;charset=UTF-8")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping("/list")
    public Map<String, String> listProvince() throws IOException {
        return provinceService.listProvince();
    }

    @GetMapping("/list/{provinceId}")
    public Map<String, String> listDistrict(@PathVariable(name = "provinceId") String provinceId) throws IOException{
        return provinceService.listDistrict(provinceId);
    }

    @GetMapping("/list/{provinceId}/{districtId}")
    public Map<String, String> listWard(@PathVariable(name = "provinceId") String provinceId,
                                       @PathVariable(name = "districtId") String districtId) throws IOException{
        return provinceService.listWard(provinceId, districtId);
    }

    @GetMapping("/find/{provinceId}")
    public String getProvince(@PathVariable(name = "provinceId") String provinceId) throws IOException{
        return provinceService.getProvince(provinceId);
    }

    @GetMapping("/find/{provinceId}/{districtId}")
    public String getDistrict(@PathVariable(name = "provinceId") String provinceId,
                              @PathVariable(name = "districtId") String districtId) throws IOException{
        return provinceService.getDistrict(provinceId, districtId);
    }

    @GetMapping("/find/{provinceId}/{districtId}/{wardId}")
    public String getWard(@PathVariable(name = "provinceId") String provinceId,
                          @PathVariable(name = "districtId") String districtId,
                          @PathVariable(name = "wardId") String wardId) throws IOException{
        return provinceService.getWard(provinceId, districtId, wardId);
    }
}
