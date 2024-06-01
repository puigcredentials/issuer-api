package es.puig.issuer.domain.model;

import es.puig.issuer.domain.model.dto.GlobalErrorMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalErrorMessageTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        LocalDateTime expectedTimestamp = LocalDateTime.now();
        int expectedStatus = 500;
        String expectedError = "Internal Server Error";
        String expectedMessage = "Unexpected error occurred";
        String expectedPath = "/api/some-endpoint";
        // Act
        GlobalErrorMessage responseError = new GlobalErrorMessage(
                expectedTimestamp,
                expectedStatus,
                expectedError,
                expectedMessage,
                expectedPath
        );
        // Assert
        assertEquals(expectedTimestamp, responseError.timestamp());
        assertEquals(expectedStatus, responseError.status());
        assertEquals(expectedError, responseError.error());
        assertEquals(expectedMessage, responseError.message());
        assertEquals(expectedPath, responseError.path());
    }

    @Test
    void lombokGeneratedMethodsTest() {
        // Arrange
        LocalDateTime expectedTimestamp = LocalDateTime.now();
        int expectedStatus = 500;
        String expectedError = "Internal Server Error";
        String expectedMessage = "Unexpected error occurred";
        String expectedPath = "/api/some-endpoint";
        // Act
        GlobalErrorMessage error1 = new GlobalErrorMessage(
                expectedTimestamp,
                expectedStatus,
                expectedError,
                expectedMessage,
                expectedPath
        );
        GlobalErrorMessage error2 = error1;

        // Assert
        assertEquals(error1, error2); // Tests equals() method generated by Lombok
        assertEquals(error1.hashCode(), error2.hashCode()); // Tests hashCode() method generated by Lombok
    }

}
