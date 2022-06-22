package com.sinsuren.sample.springboottemplate.config;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HttpClientConfig {

    private int connectionTimeout = -1;

    private int connectionRequestTimeout = -1;

    private Integer defaultMaxConnectionsPerHost;

    private Integer maxTotalConnections;

    private int soTimeout = -1;

    private boolean soReuseAddress;

    private Integer soLinger;

    private boolean soKeepAlive;

    private boolean tcpNoDelay;

    private int connectionKeepAliveTimeInMillis = 3000;

    private String userAgent;

    @Builder
    public HttpClientConfig(final int connectionTimeout, final int connectionRequestTimeout, final int defaultMaxConnectionsPerHost,
                            final Integer maxTotalConnections, final int soTimeout, final boolean soReuseAddress, final Integer soLinger, final boolean soKeepAlive,
                            final boolean tcpNoDelay, final int connectionKeepAliveTimeInMillis) {
        this.connectionTimeout = connectionTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
        this.maxTotalConnections = maxTotalConnections;
        this.soTimeout = soTimeout;
        this.soReuseAddress = soReuseAddress;
        this.soLinger = soLinger;
        this.soKeepAlive = soKeepAlive;
        this.tcpNoDelay = tcpNoDelay;
        this.connectionKeepAliveTimeInMillis = connectionKeepAliveTimeInMillis;
        this.userAgent = "testUserAgent";

    }
}
