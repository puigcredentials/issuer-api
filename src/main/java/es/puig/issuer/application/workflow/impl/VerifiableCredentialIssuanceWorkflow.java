package es.puig.issuer.application.workflow.impl;

import es.puig.issuer.domain.model.dto.*;
import reactor.core.publisher.Mono;

public interface VerifiableCredentialIssuanceWorkflow {
    Mono<Void> completeWithdrawnCredentialProcess(String processId, String type, WithdrawnCredentialRequest withdrawnCredentialRequest);

    Mono<VerifiableCredentialResponse> generateVerifiableCredentialResponse(String processId, CredentialRequest credentialRequest, String token);

    Mono<BatchCredentialResponse> generateVerifiableCredentialBatchResponse(String username, BatchCredentialRequest batchCredentialRequest, String token);

    Mono<VerifiableCredentialResponse> generateVerifiableCredentialDeferredResponse(String processId, DeferredCredentialRequest deferredCredentialRequest);

    Mono<Void> bindAccessTokenByPreAuthorizedCode(String processId, AuthServerNonceRequest authServerNonceRequest);
}
