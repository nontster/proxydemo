package com.vayu.proxydemo;


import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ProxyCustomizer implements RestTemplateCustomizer {

    private String proxyHost;
    private String proxyPort;

    private String proxyUsername;
    private String proxyPassword;
    private static Logger LOG = LoggerFactory.getLogger(ProxyCustomizer.class);

    public ProxyCustomizer(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        LOG.info("Connecting using proxy " + this.proxyHost + ":" + this.proxyPort);

        HttpHost proxy = new HttpHost(this.proxyHost, Integer.parseInt(this.proxyPort));

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(this.proxyUsername, this.proxyPassword);
        provider.setCredentials(AuthScope.ANY, credentials);

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                    @Override
                    public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
                        return super.determineProxy(target, request, context);
                    }
                })
                .build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
