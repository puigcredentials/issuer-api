package es.puig.issuer.domain.service.impl;

import es.puig.issuer.domain.model.dto.CustomCredentialOffer;
import es.puig.issuer.domain.model.dto.Grant;
import es.puig.issuer.domain.service.CredentialOfferService;
import es.puig.issuer.infrastructure.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static es.puig.issuer.domain.util.Constants.*;
import static es.puig.issuer.domain.util.EndpointsConstants.CREDENTIAL_OFFER;
import static es.puig.issuer.domain.util.EndpointsConstants.OPENID_CREDENTIAL_OFFER;
import static es.puig.issuer.domain.util.HttpUtils.ensureUrlHasProtocol;

@Slf4j
@Service
@RequiredArgsConstructor
public class CredentialOfferServiceImpl implements CredentialOfferService {

    private final AppConfig appConfig;

    @Override
    public Mono<CustomCredentialOffer> buildCustomCredentialOffer(String credentialType, Grant grant) {
        return Mono.just(CustomCredentialOffer.builder()
                .credentialIssuer(appConfig.getIssuerApiExternalDomain())
                .credentialConfigurationIds(List.of(STUDENT_CREDENTIAL))
                .grants(Map.of(GRANT_TYPE, grant))
                .build());
    }

    @Override
    public Mono<String> createCredentialOfferUri(String nonce) {
        String url = ensureUrlHasProtocol(appConfig.getIssuerApiExternalDomain() + CREDENTIAL_OFFER + "/" + nonce);
        String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);
        return Mono.just(OPENID_CREDENTIAL_OFFER + encodedUrl);
    }

}
