package es.puig.issuer.domain.model.dto;

import lombok.Builder;

@Builder
public record SignatureRequest(SignatureConfiguration configuration, String data) {
}
