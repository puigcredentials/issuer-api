package es.puig.issuer.domain.service.impl;

import es.puig.issuer.domain.model.dto.CredentialConfiguration;
import es.puig.issuer.domain.model.dto.CredentialDefinition;
import es.puig.issuer.domain.model.dto.CredentialIssuerMetadata;
import es.puig.issuer.domain.model.dto.StudentCredential;
import es.puig.issuer.domain.service.CredentialIssuerMetadataService;
import es.puig.issuer.infrastructure.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static es.puig.issuer.domain.util.Constants.*;
import static es.puig.issuer.domain.util.EndpointsConstants.*;
import static es.puig.issuer.domain.util.HttpUtils.ensureUrlHasProtocol;

@Slf4j
@Service
@RequiredArgsConstructor
public class CredentialIssuerMetadataServiceImpl implements CredentialIssuerMetadataService {

    private final AppConfig appConfig;

    @Override
    public Mono<CredentialIssuerMetadata> generateOpenIdCredentialIssuer() {
        String credentialIssuerDomain = ensureUrlHasProtocol(appConfig.getIssuerApiExternalDomain());
        CredentialConfiguration studentCredential = CredentialConfiguration.builder()
                .format(JWT_VC_JSON)
                .cryptographicBindingMethodsSupported(List.of())
                .credentialSigningAlgValuesSupported(List.of())
                .credentialDefinition(CredentialDefinition.builder().type(List.of(STUDENT_CREDENTIAL, VERIFIABLE_CREDENTIAL)).build())
                .build();
        return Mono.just(
                CredentialIssuerMetadata.builder()
                        .credentialIssuer(credentialIssuerDomain)
                        .credentialEndpoint(credentialIssuerDomain + CREDENTIAL)
                        .batchCredentialEndpoint(credentialIssuerDomain + CREDENTIAL_BATCH)
                        .deferredCredentialEndpoint(credentialIssuerDomain + CREDENTIAL_DEFERRED)
                        .credentialConfigurationsSupported(Map.of(STUDENT_CREDENTIAL, studentCredential))
                        .build()
        );
    }

}
