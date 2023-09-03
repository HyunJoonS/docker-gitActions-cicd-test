package com.lulla_nft.lulla_nft_public.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${remote.server}")
    private String baseUrl;
    
    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 600000)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(600000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(600000, TimeUnit.MILLISECONDS));
            });

        // Memory 조정 : 10MB(default 256KB)
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configure -> configure.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
            .build();

        return WebClient.builder()
            .baseUrl(baseUrl)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .filter(
                ExchangeFilterFunction.ofRequestProcessor(
                    clientRequest -> {
                        log.info(">>>>>>>>>> REQUEST <<<<<<<<<<");
                        log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                        return Mono.just(clientRequest);
                    }
                )
            )
            .exchangeStrategies(exchangeStrategies)
            .build();
    }

}
