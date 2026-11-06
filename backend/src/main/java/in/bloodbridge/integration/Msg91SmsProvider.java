package in.bloodbridge.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "bloodbridge.notifications.sms.provider", havingValue = "MSG91")
public class Msg91SmsProvider implements SmsProvider {
    private static final Logger log = LoggerFactory.getLogger(Msg91SmsProvider.class);


    @Override
    public boolean sendSms(String phone, String templateId, Map<String, String> params, String fallbackMessage) {
        log.info("[MSG91_SMS_PROVIDER] Sending DLT compliant SMS to {} via Msg91 Flow: {}", phone, templateId);
        // Real HTTP call placeholder with externalized auth key
        return true;
    }

    @Override
    public String getProviderName() {
        return "MSG91";
    }
}
