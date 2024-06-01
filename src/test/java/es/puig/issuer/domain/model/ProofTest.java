package es.puig.issuer.domain.model;

import es.puig.issuer.domain.model.dto.Proof;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProofTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedProofType = "jwt_vc_json";
        String expectedJwt = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiw....WZwmhmn9OQp6YxX0a2L";
        // Act
        Proof proof = new Proof(expectedProofType, expectedJwt);
        // Assert
        assertEquals(expectedProofType, proof.proofType());
        assertEquals(expectedJwt, proof.jwt());
    }

    @Test
    void lombokGeneratedMethodsTest() {
        // Arrange
        String expectedProofType = "jwt_vc_json";
        String expectedJwt = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiw....WZwmhmn9OQp6YxX0a2L";
        // Act
        Proof dto1 = new Proof(expectedProofType, expectedJwt);
        Proof dto2 = new Proof(expectedProofType, expectedJwt);
        // Assert
        assertEquals(dto1, dto2); // Tests equals() method generated by Lombok
        assertEquals(dto1.hashCode(), dto2.hashCode()); // Tests hashCode() method generated by Lombok
    }

}
