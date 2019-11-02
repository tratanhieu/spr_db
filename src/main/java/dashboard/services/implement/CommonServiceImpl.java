package dashboard.services.implement;

import dashboard.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private MessageSource messageSource;


    @Override
    public String getMessageSource(String key) {
        return messageSource.getMessage(key, null, null);
    }

    @Override
    public String getMessageSource(String key, Object[] params) {
        return messageSource.getMessage(key, params, null);
    }

    @Override
    public String getMessageSource(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }
}
