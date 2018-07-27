package br.com.labbs.workout.httpclientbattle.shared;

import java.util.Map;

public enum Env {

    URL_SERVER;

    final Map<String, String> envVars = System.getenv();

    public int getInt(){
        return Integer.parseInt(envVars.get(this.name()).trim());
    }

    public String get(){
        return envVars.get(this.name());
    }
}
