package es.puig.issuer.infrastructure.config;

import es.puig.issuer.domain.model.dto.CustomCredentialOffer;
import es.puig.issuer.domain.model.dto.VerifiableCredentialJWT;
import es.puig.issuer.infrastructure.repository.CacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class CacheStoreConfig {

    private final AppConfig appConfig;

    @Bean
    public CacheStore<String> cacheStoreDefault() {
        return new CacheStore<>(10, TimeUnit.MINUTES);
    }

    @Bean
    public CacheStore<VerifiableCredentialJWT> cacheStoreForVerifiableCredentialJwt() {
        return new CacheStore<>(appConfig.getCacheLifetimeForVerifiableCredential(), TimeUnit.MINUTES);
    }

    @Bean
    public CacheStore<CustomCredentialOffer> cacheStoreForCredentialOffer() {
        return new CacheStore<>(appConfig.getCacheLifetimeForCredentialOffer(), TimeUnit.MINUTES);
    }

}
