package dashboard.constants;

import java.util.HashMap;
import java.util.Map;

public class FeatureConstants {
    public static final Map<String, String> MAP_FEATURE = new HashMap<String, String>(){{
        put("USER", "Manage User");
        put("PRODUCT", "Manage Product");
        put("USER_GROUP", "Manage User Group");
        put("ORDER", "Manage Order");
        put("PROMOTION", "Manage Promotion");
        put("POST", "Manage Post");
    }};
}
