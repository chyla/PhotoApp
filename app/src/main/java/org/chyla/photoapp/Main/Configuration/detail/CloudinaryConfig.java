package org.chyla.photoapp.Main.Configuration.detail;

public class CloudinaryConfig {

    private final String cloudName;
    private final String apiKey;
    private final String apiSecret;

    public CloudinaryConfig(final String cloudName, final String apiKey, final String apiSecret) {
        this.cloudName = cloudName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public String getCloudName() {
        return cloudName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

}
