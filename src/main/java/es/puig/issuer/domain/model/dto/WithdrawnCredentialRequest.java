package es.puig.issuer.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

@Builder
public record WithdrawnCredentialRequest(@JsonProperty("credential") JsonNode credential) {
}
