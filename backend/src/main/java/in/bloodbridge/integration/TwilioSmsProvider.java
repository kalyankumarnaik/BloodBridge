package in.bloodbridge.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "bloodbridge.notifications.sms.provider", havingValue = "TWILIO")
public class TwilioSmsProvider implements SmsProvider {
    private static final Logger log = LoggerFactory.getLogger(TwilioSmsProvider.class);


    @Override
    public boolean sendSms(String phone, String templateId, Map<String, String> params, String fallbackMessage) {
        log.info("[TWILIO_SMS_PROVIDER] Sending Twilio SMS to {}: {}", phone, fallbackMessage);
        return true;
    }

    @Override
    public String getProviderName() {
        return "TWILIO";
    }
}
