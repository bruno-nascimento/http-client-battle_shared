package br.com.labbs.workout.httpclientbattle.shared;

import io.opentracing.Scope;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.opentracing.tag.Tags;

import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unused")
public interface HttpClient {

    int hit(int requestNumber);
    String getClientName();
    void addHeaderToRequest(Object request, String key, String value);
    Object getRequest();
    int getResponseStatusCode();

    default int doRequest(int requestNumber) {
        try(final Scope scope = Tracing.get().buildSpan(this.getClientName()).withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CLIENT).startActive(true)) {
            Tracing.get().inject(scope.span().context(), Format.Builtin.HTTP_HEADERS, this.jaegerHeaderInjector(getRequest()));

            this.hit(requestNumber);

            scope.span().setTag("client_type", getClientName());
            scope.span().setTag("request_number", requestNumber);
            scope.span().setTag("status_code", getResponseStatusCode());
            scope.span().log(""+getResponseStatusCode());
            return getResponseStatusCode();
        } catch (Throwable e) {
            e.printStackTrace();
            return 500;
        }
    }

    default TextMap jaegerHeaderInjector(final Object request) {
        return new TextMap() {
            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                throw new UnsupportedOperationException("carrier is write-only");
            }

            @Override
            public void put(String key, String value) {
                HttpClient.this.addHeaderToRequest(request, key, value);
            }
        };
    }

}
