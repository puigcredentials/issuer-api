package es.puig.issuer.domain.model.dto;

import es.puig.issuer.domain.model.enums.SignatureType;
import lombok.Builder;

@Builder
public record SignedData(SignatureType type, String data) {
}
