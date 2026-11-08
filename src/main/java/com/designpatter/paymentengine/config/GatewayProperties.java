package com.designpatter.paymentengine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "designpatter.paymentengine")
public class GatewayProperties {

    private int timeoutMs;
    private Map<String, BankConfig> providers = new HashMap<>();

    public static class BankConfig {
        private boolean enabled;
        private String apiUrl;
        private String merchantId;
        private String terminalId;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public String getApiUrl() { return apiUrl; }
        public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
        public String getMerchantId() { return merchantId; }
        public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
        public String getTerminalId() { return terminalId; }
        public void setTerminalId(String terminalId) { this.terminalId = terminalId; }
    }

    public int getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(int timeoutMs) { this.timeoutMs = timeoutMs; }
    public Map<String, BankConfig> getProviders() { return providers; }
    public void setProviders(Map<String, BankConfig> providers) { this.providers = providers; }
}
