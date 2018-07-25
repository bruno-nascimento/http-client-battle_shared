package br.com.labbs.workout.httpclientbattle.shared;

import io.opentracing.propagation.TextMap;

public interface HttpClient {

    int hit(int requestNumber);
    String getClientName();
    TextMap requestBuilderCarrier(final Object request); // {
//        return new TextMap() {
//            @Override
//            public Iterator<Map.Entry<String, String>> iterator() {
//                throw new UnsupportedOperationException("carrier is write-only");
//            }
//
//            @Override
//            public void put(String key, String value) {
//                request.addHeader(key, value);
//            }
//        };
//    }

}
