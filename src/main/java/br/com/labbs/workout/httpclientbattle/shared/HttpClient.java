package br.com.labbs.workout.httpclientbattle.shared;

import io.opentracing.Scope;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.opentracing.tag.Tags;

import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unused")
public interface HttpClient<Request, Response> {

    String getClientName();
    Request newRequest(String url);
    void addHeaderToRequest(Request request, String key, String value);
    Response execRequest(Request request, int requestNumber) throws Exception;
    int getResponseStatusCode(Response response);

    default int doRequest(int requestNumber) {
        try(final Scope scope = Tracing.get().buildSpan(this.getClientName()).withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CLIENT).startActive(true)) {
            Request request = this.newRequest(Env.URL_SERVER.get());
            Tracing.get().inject(scope.span().context(), Format.Builtin.HTTP_HEADERS, this.jaegerHeaderInjector(request));

            Response response = this.execRequest(request, requestNumber);

            scope.span().setTag("client_type", getClientName());
            scope.span().setTag("request_number", requestNumber);
            scope.span().setTag("status_code", getResponseStatusCode(response));
            scope.span().log(""+getResponseStatusCode(response));
            return getResponseStatusCode(response);
        } catch (Throwable e) {
            e.printStackTrace();
            return 500;
        }
    }

    default TextMap jaegerHeaderInjector(final Request request) {
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
