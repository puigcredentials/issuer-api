package es.puig.issuer.infrastructure.config.adapter;

import es.puig.issuer.infrastructure.config.adapter.impl.YamlConfigAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YamlConfigAdapterTest {
    @Test
    void getConfiguration_returnsKey() {
        // Arrange
        YamlConfigAdapter yamlConfigAdapter = new YamlConfigAdapter();
        String key = "testKey";

        // Act
        String result = yamlConfigAdapter.getConfiguration(key);

        // Assert
        assertEquals(key, result);
    }
}
