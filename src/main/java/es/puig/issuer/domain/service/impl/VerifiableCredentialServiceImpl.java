package es.puig.issuer.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import es.puig.issuer.domain.model.dto.DeferredCredentialRequest;
import es.puig.issuer.domain.model.dto.StudentCredential;
import es.puig.issuer.domain.model.dto.VerifiableCredentialResponse;
import es.puig.issuer.domain.model.dto.WithdrawnCredentialRequest;
import es.puig.issuer.domain.service.CredentialProcedureService;
import es.puig.issuer.domain.service.DeferredCredentialMetadataService;
import es.puig.issuer.domain.service.VerifiableCredentialService;
import es.puig.issuer.domain.util.factory.CredentialFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;


@Service
@RequiredArgsConstructor
@Slf4j
public class VerifiableCredentialServiceImpl implements VerifiableCredentialService {
    private final ObjectMapper objectMapper;
    private final CredentialFactory credentialFactory;
    private final CredentialProcedureService credentialProcedureService;
    private final DeferredCredentialMetadataService deferredCredentialMetadataService;

    @Override
    public Mono<String> generateVc(String processId, String vcType, WithdrawnCredentialRequest withdrawnCredentialRequest) {
        return credentialFactory.mapCredentialIntoACredentialProcedureRequest(processId, vcType, withdrawnCredentialRequest.credential())
                .flatMap(credentialProcedureService::createCredentialProcedure)
                .flatMap(deferredCredentialMetadataService::createDeferredCredentialMetadata);

    }

    @Override
    public Mono<VerifiableCredentialResponse> generateDeferredCredentialResponse(String processId, DeferredCredentialRequest deferredCredentialRequest) {
        return deferredCredentialMetadataService.getVcByTransactionId(deferredCredentialRequest.transactionId())
                .flatMap(deferredCredentialMetadataDeferredResponse -> {
                    if (deferredCredentialMetadataDeferredResponse.vc() != null){
                        return deferredCredentialMetadataService.deleteDeferredCredentialMetadataById(deferredCredentialMetadataDeferredResponse.id())
                                .then(Mono.just(VerifiableCredentialResponse.builder()
                                        .credential(deferredCredentialMetadataDeferredResponse.vc())
                                        .build()));
                    }
                    else {
                        return Mono.just(VerifiableCredentialResponse.builder()
                                        .transactionId(deferredCredentialMetadataDeferredResponse.transactionId())
                                .build());
                    }

                });
    }

    @Override
    public Mono<Void> bindAccessTokenByPreAuthorizedCode(String processId, String accessToken, String preAuthCode) {
        try {
            JWSObject jwsObject = JWSObject.parse(accessToken);
            String newAuthServerNonce = jwsObject.getPayload().toJSONObject().get("jti").toString();
            return deferredCredentialMetadataService.updateAuthServerNonceByAuthServerNonce(newAuthServerNonce, preAuthCode);
        } catch (ParseException e){
            throw new RuntimeException();
        }

    }

    @Override
    public Mono<VerifiableCredentialResponse> buildCredentialResponse(String processId, String subjectDid, String authServerNonce, String format) {
            return deferredCredentialMetadataService.getProcedureIdByAuthServerNonce(authServerNonce)
                    .flatMap(procedureId -> credentialProcedureService.getCredentialTypeByProcedureId(procedureId)
                            .flatMap(credentialType -> credentialProcedureService.getDecodedCredentialByProcedureId(procedureId)
                                    .flatMap(credential -> credentialFactory.mapCredentialAndBindMandateeId(processId, credentialType, credential, subjectDid)
                                            .flatMap(bindCredential -> credentialProcedureService.updateDecodedCredentialByProcedureId(procedureId, bindCredential, format)
                                                    .then(deferredCredentialMetadataService.updateDeferredCredentialMetadataByAuthServerNonce(authServerNonce, format)
                                                            .flatMap(transactionId -> {
                                                                try {
                                                                    // Extract the "vc" object
                                                                    JsonNode vcNode = objectMapper.readTree(bindCredential).get("vc");

                                                                    StudentCredential studentCredential = objectMapper.treeToValue(vcNode, StudentCredential.class);

                                                                    String bindStudentCredentialJson = objectMapper.writeValueAsString(studentCredential);

                                                                    return Mono.just(VerifiableCredentialResponse.builder()
                                                                            .credential(bindStudentCredentialJson)
                                                                            .transactionId(transactionId)
                                                                            .build());
                                                                } catch (JsonProcessingException e) {
                                                                    log.error("Error processing JSON", e);
                                                                    return Mono.error(e);
                                                                }
                                                            }))))));
    }
}
