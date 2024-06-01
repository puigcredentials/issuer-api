package es.puig.issuer.domain.service;


import es.puig.issuer.domain.model.dto.SignatureRequest;
import es.puig.issuer.domain.model.dto.SignedData;
import reactor.core.publisher.Mono;

public interface RemoteSignatureService {
    Mono<SignedData> sign(SignatureRequest signatureRequest, String token);
}
