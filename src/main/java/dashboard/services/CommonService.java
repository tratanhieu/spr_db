package dashboard.services;

import java.util.Locale;

public interface CommonService {
    String getMessageSource(String key);
    String getMessageSource(String key, Object[] params);
    String getMessageSource(String key, Object[] params, Locale locale);
}
