package in.bloodbridge.integration;

import java.util.Map;

public interface SmsProvider {
    boolean sendSms(String phone, String templateId, Map<String, String> params, String fallbackMessage);
    String getProviderName();
}
