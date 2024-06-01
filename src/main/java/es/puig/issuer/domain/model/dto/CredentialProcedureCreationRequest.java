package es.puig.issuer.domain.model.dto;

import lombok.Builder;

@Builder
public record CredentialProcedureCreationRequest(
        String credentialId,
        String organizationIdentifier,
        String credentialDecoded
        )
{
}
