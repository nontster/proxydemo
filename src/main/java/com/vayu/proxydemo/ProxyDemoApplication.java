package com.vayu.proxydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProxyDemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyDemoApplication.class);
	@Value("${proxy.enabled}")
	boolean proxyEnabled;
    @Value("${proxy.host}")
    String proxyHost;
    @Value("${proxy.port}")
    String proxyPort;
    @Value("${proxy.username}")
    String proxyUsername;
    @Value("${proxy.password}")
    String proxyPassword;

    public static void main(String[] args) {
        SpringApplication.run(ProxyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Calling httpbin.org");

        RestTemplate restTemplate = (proxyEnabled)? new RestTemplateBuilder(
                new ProxyCustomizer(this.proxyHost, this.proxyPort,this.proxyUsername, this.proxyPassword))
                .build(): new RestTemplate();
        UUID uuid = restTemplate.getForObject("https://httpbin.org/uuid", UUID.class);
        LOG.info(uuid.toString());
    }
}
