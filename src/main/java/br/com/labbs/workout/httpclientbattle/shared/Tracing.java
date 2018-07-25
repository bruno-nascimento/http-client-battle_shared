package br.com.labbs.workout.httpclientbattle.shared;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;

public class Tracing {

    private Tracing() {
    }

    public static JaegerTracer init() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(true);

        Configuration config = Configuration.fromEnv()
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        return config.getTracer();
    }

}
