package com.sinsuren.sample.springboottemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;

@Slf4j
public class HttpClientFactory {
    public static HttpClient createHttpClient(final HttpClientConfig httpClientConfig) {

        final SocketConfig.Builder socketConfigBuilder = createSocketConfig(httpClientConfig);
        final PoolingHttpClientConnectionManager connectionManager = createHttpClientManager(httpClientConfig, socketConfigBuilder);
        final HttpClientBuilder httpClientbuilder = HttpClients.custom().setConnectionManager(connectionManager);
        final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(httpClientConfig.getConnectionTimeout())
                .setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout())
                .setSocketTimeout(httpClientConfig.getSoTimeout()).build();
        httpClientbuilder.setDefaultRequestConfig(requestConfig);
        httpClientbuilder.setKeepAliveStrategy((response, context) -> {
            if (response.containsHeader(HTTP.CONN_KEEP_ALIVE)) {
                try {
                    final Header header = response.getFirstHeader(HTTP.CONN_KEEP_ALIVE);
                    return Long.parseLong(header.getValue()) * 1000; // in millis
                } catch (final Exception ex) {
                    return httpClientConfig.getConnectionKeepAliveTimeInMillis();
                }
            } else {
                return httpClientConfig.getConnectionKeepAliveTimeInMillis();
            }
        });
        return httpClientbuilder.build();
    }

    private static PoolingHttpClientConnectionManager createHttpClientManager(final HttpClientConfig httpConnectionManagerConfig,
                                                                              final SocketConfig.Builder socketConfigBuilder) {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpConnectionManagerConfig.getMaxTotalConnections());
        connectionManager.setDefaultMaxPerRoute(httpConnectionManagerConfig.getDefaultMaxConnectionsPerHost());
        connectionManager.setDefaultSocketConfig(socketConfigBuilder.build());
        return connectionManager;
    }

    private static SocketConfig.Builder createSocketConfig(final HttpClientConfig httpConnectionManagerConfig) {
        final SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        socketConfigBuilder.setSoTimeout(httpConnectionManagerConfig.getSoTimeout());
        socketConfigBuilder.setSoReuseAddress(httpConnectionManagerConfig.isSoReuseAddress());
        socketConfigBuilder.setSoLinger(httpConnectionManagerConfig.getSoLinger());
        socketConfigBuilder.setSoKeepAlive(httpConnectionManagerConfig.isSoKeepAlive());
        socketConfigBuilder.setTcpNoDelay(httpConnectionManagerConfig.isTcpNoDelay());
        return socketConfigBuilder;
    }
}
