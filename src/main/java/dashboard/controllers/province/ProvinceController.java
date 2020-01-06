package dashboard.controllers.province;

import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/province", produces = "application/json;charset=UTF-8")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping("")
    public ResponseEntity listProvince() throws IOException {
        return ResponseEntity.ok(provinceService.listProvince());
    }

    @GetMapping("{provinceId}")
    public ResponseEntity listDistrict(@PathVariable(name = "provinceId") String provinceId) throws IOException{
        return ResponseEntity.ok(provinceService.listDistrict(provinceId));
    }

    @GetMapping("{provinceId}/{districtId}")
    public ResponseEntity listWard(@PathVariable(name = "provinceId") String provinceId,
                                       @PathVariable(name = "districtId") String districtId) throws IOException{
        return ResponseEntity.ok(provinceService.listWard(provinceId, districtId));
    }

    @GetMapping("get-address/{provinceId}/{districtId}/{wardId}")
    public ResponseEntity getAddress(@PathVariable(name = "provinceId") String provinceId,
                                   @PathVariable(name = "districtId") String districtId,
                                     @PathVariable(name = "wardId") String wardId) throws IOException{
        return ResponseEntity.ok(provinceService.getAddress(provinceId, districtId, wardId));
    }
}
