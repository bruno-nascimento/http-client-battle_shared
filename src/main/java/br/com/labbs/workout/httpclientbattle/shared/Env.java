package br.com.labbs.workout.httpclientbattle.shared;

import java.util.Map;

public enum Env {

    URL_SERVER,
    MAX_REQUEST_PER_MINUTE,
    TEST_DURATION_SECONDS,
    PROMETHEUS_PUSHGATEWAY;

    final Map<String, String> envVars = System.getenv();

    public int getInt(){
        return Integer.parseInt(envVars.get(this.name()).trim());
    }

    public String get(){
        return envVars.get(this.name());
    }
}
