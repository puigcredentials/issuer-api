package es.puig.issuer.domain.service;

import reactor.core.publisher.Mono;

public interface AccessTokenService {
    Mono<String> getCleanBearerToken(String authorizationHeader);
    Mono<String> getUserIdFromHeader(String authorizationHeader);
}
