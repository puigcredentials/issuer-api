package es.puig.issuer.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record BatchCredentialRequest(
        @JsonProperty("credential_requests") List<CredentialRequest> credentialRequests
        ) {
}
