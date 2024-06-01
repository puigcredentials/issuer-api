package es.puig.issuer.domain.service;

import es.puig.issuer.domain.exception.UserDoesNotExistException;
import es.puig.issuer.domain.model.dto.CommitCredential;
import es.puig.issuer.domain.model.dto.SubjectDataResponse;
import reactor.core.publisher.Mono;

public interface AuthenticSourcesRemoteService {

    Mono<SubjectDataResponse> getUser(String token) throws UserDoesNotExistException;

    Mono<String> getUserFromLocalFile() throws UserDoesNotExistException;

    Mono<Void> commitCredentialSourceData(CommitCredential commitCredential, String token);
}
