package es.puig.issuer.domain.service;

import es.puig.issuer.domain.model.dto.CustomCredentialOffer;
import es.puig.issuer.domain.model.dto.Grant;
import reactor.core.publisher.Mono;

public interface CredentialOfferService {
    Mono<CustomCredentialOffer> buildCustomCredentialOffer(String credentialType, Grant grant);
    Mono<String> createCredentialOfferUri(String nonce);
}
