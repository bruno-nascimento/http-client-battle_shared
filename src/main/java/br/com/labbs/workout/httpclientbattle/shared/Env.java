package br.com.labbs.workout.httpclientbattle.shared;

import java.util.Map;

public enum Env {

    JAEGER_SERVICE_NAME,
    JAEGER_AGENT_HOST,
    MAX_REQUEST_PER_MINUTE,
    TEST_DURATION_SECONDS,
    PROMETHEUS_PUSHGATEWAY,
    URL_SERVER_PROTOCOL,
    URL_SERVER_DOMAIN,
    URL_SERVER_PORT,
    URL_SERVER_PATH,
    URL_SERVER;

    final Map<String, String> envVars = System.getenv();

    public int getInt(){
        return Integer.parseInt(envVars.get(this.name()).trim());
    }

    public String get(){
        return envVars.get(this.name());
    }
}
