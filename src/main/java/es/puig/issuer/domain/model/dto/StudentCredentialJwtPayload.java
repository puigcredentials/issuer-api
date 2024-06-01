package es.puig.issuer.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record StudentCredentialJwtPayload(
        @JsonProperty("sub")
        String subject,

        @JsonProperty("nbf")
        String notValidBefore,

        @JsonProperty("iss")
        String issuer,

        @JsonProperty("exp")
        String expirationTime,

        @JsonProperty("iat")
        String issuedAt,

        @JsonProperty("vc")
        StudentCredential studentCredential,

        @JsonProperty("jti")
        String JwtId
) {
}
