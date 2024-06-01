package es.puig.issuer.domain.util.factory;

import com.fasterxml.jackson.databind.JsonNode;
import es.puig.issuer.domain.exception.CredentialTypeUnsupportedException;
import es.puig.issuer.domain.model.dto.CredentialProcedureCreationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static es.puig.issuer.domain.util.Constants.STUDENT_CREDENTIAL;

@Component
@RequiredArgsConstructor
@Slf4j
public class CredentialFactory {
    public final StudentCredentialFactory studentCredentialFactory;
    public Mono<CredentialProcedureCreationRequest> mapCredentialIntoACredentialProcedureRequest(String processId, String credentialType, JsonNode credential){
        if (credentialType.equals(STUDENT_CREDENTIAL)) {
            return studentCredentialFactory.mapAndBuildStudentCredential(credential)
                    .doOnSuccess(studentCredential -> log.info("ProcessID: {} - Credential mapped: {}", processId, credential));
        }
        return Mono.error(new CredentialTypeUnsupportedException(credentialType));
    }
    public Mono<String> mapCredentialAndBindMandateeId(String processId, String credentialType, String credential, String mandateeId){
        if (credentialType.equals(STUDENT_CREDENTIAL)) {
            return studentCredentialFactory.mapCredentialAndBindMandateeIdInToTheCredential(credential, mandateeId)
                    .doOnSuccess(studentCredential -> log.info("ProcessID: {} - Credential mapped and bind to the id: {}", processId, studentCredential));
        }
        return Mono.error(new CredentialTypeUnsupportedException(credentialType));
    }



}
