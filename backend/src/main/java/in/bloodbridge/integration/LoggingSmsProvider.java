package in.bloodbridge.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "bloodbridge.notifications.sms.provider", havingValue = "LOGGING", matchIfMissing = true)
public class LoggingSmsProvider implements SmsProvider {
    private static final Logger log = LoggerFactory.getLogger(LoggingSmsProvider.class);


    @Override
    public boolean sendSms(String phone, String templateId, Map<String, String> params, String fallbackMessage) {
        log.info("[LOGGING_SMS_PROVIDER] Destination: {} | TemplateId: {} | Params: {} | Fallback: {}",
                maskPhone(phone), templateId, params, fallbackMessage);
        return true;
    }

    @Override
    public String getProviderName() {
        return "LOGGING";
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 6) return "******";
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 2);
    }
}
