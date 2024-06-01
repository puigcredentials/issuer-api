package es.puig.issuer.domain.service;

import es.puig.issuer.domain.model.dto.DeferredCredentialRequest;
import es.puig.issuer.domain.model.dto.WithdrawnCredentialRequest;
import es.puig.issuer.domain.model.dto.VerifiableCredentialResponse;
import reactor.core.publisher.Mono;

public interface VerifiableCredentialService {
    Mono<String> generateVc(String processId, String vcType, WithdrawnCredentialRequest withdrawnCredentialRequest);
//    Mono<String> generateVcPayLoad(String vcTemplate, String subjectDid, String issuerDid, String userData, Instant expiration);
    Mono<VerifiableCredentialResponse> buildCredentialResponse(String processId, String subjectDid, String authServerNonce, String format);

    Mono<Void> bindAccessTokenByPreAuthorizedCode(String processId, String accessToken, String preAuthCode);
    Mono<VerifiableCredentialResponse> generateDeferredCredentialResponse(String processId, DeferredCredentialRequest deferredCredentialRequest);
}
