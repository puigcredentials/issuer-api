package es.puig.issuer.application.workflow.impl;

import es.puig.issuer.domain.model.dto.PendingCredentials;
import es.puig.issuer.domain.model.dto.SignedCredentials;
import reactor.core.publisher.Mono;

public interface DeferredCredentialWorkflow {

    Mono<PendingCredentials> getPendingCredentialsByOrganizationId(String organizationId);

    Mono<Void> updateSignedCredentials(SignedCredentials signedCredentials);

}
