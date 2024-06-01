package es.puig.issuer.application.workflow.impl;

import es.puig.issuer.domain.model.dto.CustomCredentialOffer;
import reactor.core.publisher.Mono;

public interface CredentialOfferIssuanceWorkflow {
    Mono<String> buildCredentialOfferUri(String processId, String transactionCode);
    Mono<CustomCredentialOffer> getCustomCredentialOffer(String nonce);
}
