package es.puig.issuer.domain.service;

import es.puig.issuer.domain.model.dto.CustomCredentialOffer;
import reactor.core.publisher.Mono;

public interface CredentialOfferCacheStorageService {

    Mono<String> saveCustomCredentialOffer(CustomCredentialOffer customCredentialOffer);

    Mono<CustomCredentialOffer> getCustomCredentialOffer(String nonce);

}
