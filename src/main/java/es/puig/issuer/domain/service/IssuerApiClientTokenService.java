package es.puig.issuer.domain.service;

import reactor.core.publisher.Mono;

public interface IssuerApiClientTokenService {
    Mono<String> getClientToken();
}
