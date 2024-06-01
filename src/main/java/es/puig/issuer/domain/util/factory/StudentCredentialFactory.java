package es.puig.issuer.domain.util.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.puig.issuer.domain.model.dto.CredentialProcedureCreationRequest;
import es.puig.issuer.domain.model.dto.StudentCredential;
import es.puig.issuer.domain.model.dto.StudentCredentialJwtPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static es.puig.issuer.domain.util.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentCredentialFactory {

    private final ObjectMapper objectMapper;

    public Mono<String> mapCredentialAndBindMandateeIdInToTheCredential(String studentCredential, String mandateeId) {
        StudentCredentialJwtPayload baseStudentCredential = mapStringToStudentCredential(studentCredential);
        return bindMandateeIdToStudentCredential(baseStudentCredential, mandateeId)
                .flatMap(this::convertStudentCredentialInToString);
    }

    public Mono<CredentialProcedureCreationRequest> mapAndBuildStudentCredential(JsonNode studentCredential) {
        StudentCredential.CredentialSubject baseStudentCredential = mapJsonNodeToCredentialSubject(studentCredential);

        return buildFinalStudentCredential(baseStudentCredential)
                .flatMap(this::buildStudentCredentialJwtPayload)
                .flatMap(studentCredentialJwtPayload -> convertStudentCredentialInToString(studentCredentialJwtPayload)
                        .flatMap(decodedCredential -> buildCredentialProcedureCreationRequest(decodedCredential, studentCredentialJwtPayload))
                );
    }

    private StudentCredentialJwtPayload mapStringToStudentCredential(String studentCredential) {
        try {
            log.info(objectMapper.readValue(studentCredential, StudentCredentialJwtPayload.class).toString());
            return objectMapper.readValue(studentCredential, StudentCredentialJwtPayload.class);
        } catch (JsonProcessingException e) {
            // fixme: handle exception and return a custom exception
            throw new RuntimeException(e);
        }
    }

    private StudentCredential.CredentialSubject mapJsonNodeToCredentialSubject(JsonNode jsonNode) {

        StudentCredential.CredentialSubject.Mandate mandate = objectMapper.convertValue(jsonNode, StudentCredential.CredentialSubject.Mandate.class);
        return StudentCredential.CredentialSubject.builder()
                .mandate(mandate)
                .build();
    }

    private Mono<StudentCredential> buildFinalStudentCredential(StudentCredential.CredentialSubject baseStudentCredential) {
        Instant currentTime = Instant.now();

        // Creando una lista nueva de powers con nuevos IDs
        List<StudentCredential.CredentialSubject.Mandate.Power> populatedPowers = baseStudentCredential.mandate().power().stream()
                .map(power -> StudentCredential.CredentialSubject.Mandate.Power.builder()
                        .id(UUID.randomUUID().toString())
                        .tmfType(power.tmfType())
                        .tmfAction(power.tmfAction())
                        .tmfFunction(power.tmfFunction())
                        .build())
                .toList();


        return Mono.just(StudentCredential.builder()
                .expirationDate(currentTime.plus(30, ChronoUnit.DAYS).toString())
                .issuanceDate(currentTime.toString())
                .validFrom(currentTime.toString())
                .id(UUID.randomUUID().toString())
                .type(List.of(STUDENT_CREDENTIAL, VERIFIABLE_CREDENTIAL))
                .issuer(baseStudentCredential.mandate().mandator().organizationIdentifier())
                .credentialSubject(StudentCredential.CredentialSubject.builder()
                        .mandate(StudentCredential.CredentialSubject.Mandate.builder()
                                .id(UUID.randomUUID().toString())
                                .mandator(baseStudentCredential.mandate().mandator())
                                .mandatee(baseStudentCredential.mandate().mandatee())
                                .power(populatedPowers)
                                .lifeSpan(StudentCredential.CredentialSubject.Mandate.LifeSpan.builder()
                                        .startDateTime(currentTime.toString())
                                        .endDateTime(currentTime.plus(30, ChronoUnit.DAYS).toString())
                                        .build())
                                .build())
                        .build())
                .build());
    }


    private Mono<StudentCredentialJwtPayload> buildStudentCredentialJwtPayload(StudentCredential studentCredential){
        return Mono.just(
                StudentCredentialJwtPayload.builder()
                        .JwtId(UUID.randomUUID().toString())
                        .studentCredential(studentCredential)
                        .expirationTime(studentCredential.expirationDate())
                        .issuedAt(studentCredential.issuanceDate())
                        .notValidBefore(studentCredential.validFrom())
                        .issuer(studentCredential.issuer())
                        .subject(studentCredential.credentialSubject().mandate().mandatee().id())
                        .build()
        );
    }

    private Mono<StudentCredentialJwtPayload> bindMandateeIdToStudentCredential(StudentCredentialJwtPayload baseStudentCredential, String mandateeId) {
        return Mono.just(
                StudentCredentialJwtPayload.builder().studentCredential(
                        StudentCredential.builder()
                                .expirationDate(baseStudentCredential.studentCredential().expirationDate())
                                .issuanceDate(baseStudentCredential.studentCredential().issuanceDate())
                                .validFrom(baseStudentCredential.studentCredential().validFrom())
                                .id(baseStudentCredential.studentCredential().id())
                                .type(baseStudentCredential.studentCredential().type())
                                .issuer(baseStudentCredential.issuer())
                                .credentialSubject(StudentCredential.CredentialSubject.builder()
                                        .mandate(StudentCredential.CredentialSubject.Mandate.builder()
                                                .id(baseStudentCredential.studentCredential().credentialSubject().mandate().id())
                                                .mandator(baseStudentCredential.studentCredential().credentialSubject().mandate().mandator())
                                                .mandatee(StudentCredential.CredentialSubject.Mandate.Mandatee.builder()
                                                        .id(mandateeId)
                                                        .email(baseStudentCredential.studentCredential().credentialSubject().mandate().mandatee().email())
                                                        .firstName(baseStudentCredential.studentCredential().credentialSubject().mandate().mandatee().firstName())
                                                        .lastName(baseStudentCredential.studentCredential().credentialSubject().mandate().mandatee().lastName())
                                                        .mobilePhone(baseStudentCredential.studentCredential().credentialSubject().mandate().mandatee().mobilePhone())
                                                        .build())
                                                .power(baseStudentCredential.studentCredential().credentialSubject().mandate().power())
                                                .lifeSpan(baseStudentCredential.studentCredential().credentialSubject().mandate().lifeSpan())
                                                .build())
                                        .build())
                                .build())
                        .subject(baseStudentCredential.subject())
                        .JwtId(baseStudentCredential.JwtId())
                        .expirationTime(baseStudentCredential.expirationTime())
                        .issuedAt(baseStudentCredential.issuedAt())
                        .notValidBefore(baseStudentCredential.notValidBefore())
                        .build());
    }

    private Mono<String> convertStudentCredentialInToString(StudentCredentialJwtPayload studentCredentialJwtPayload) {
        try {

            return Mono.just(objectMapper.writeValueAsString(studentCredentialJwtPayload));
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException());
        }
    }

    private Mono<CredentialProcedureCreationRequest> buildCredentialProcedureCreationRequest(String decodedCredential, StudentCredentialJwtPayload studentCredentialJwtPayload) {
        return Mono.just(
                CredentialProcedureCreationRequest.builder()
                        .credentialId(studentCredentialJwtPayload.studentCredential().id())
                        .organizationIdentifier(studentCredentialJwtPayload.studentCredential().credentialSubject().mandate().mandator().organizationIdentifier())
                        .credentialDecoded(decodedCredential)
                        .build()
        );
    }

}
