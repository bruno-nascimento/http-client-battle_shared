package br.com.labbs.workout.httpclientbattle.shared;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;

public class Tracing {

    private static final Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
            .withType(ConstSampler.TYPE)
            .withParam(1);
    private static final Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
            .withLogSpans(true);
    private static final Configuration config = Configuration.fromEnv()
            .withSampler(samplerConfig)
            .withReporter(reporterConfig);

    private Tracing() {
    }

    public static JaegerTracer get() {
        return config.getTracer();
    }

}
