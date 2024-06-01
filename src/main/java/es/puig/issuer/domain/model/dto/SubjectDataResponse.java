package es.puig.issuer.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Map;

@Builder
public record SubjectDataResponse(
   @JsonProperty("credentialSubjectData") Map<String, Map<String, String>> credentialSubjectData) {
}
