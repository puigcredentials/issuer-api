package es.puig.issuer.infrastructure.config;

import es.puig.issuer.infrastructure.config.adapter.ConfigAdapter;
import es.puig.issuer.infrastructure.config.adapter.factory.ConfigAdapterFactory;
import es.puig.issuer.infrastructure.config.properties.RemoteSignatureProperties;
import org.springframework.stereotype.Component;

@Component
public class RemoteSignatureConfig {

    private final ConfigAdapter configAdapter;
    private final RemoteSignatureProperties remoteSignatureProperties;

    public RemoteSignatureConfig(ConfigAdapterFactory configAdapterFactory, RemoteSignatureProperties remoteSignatureProperties) {
        this.configAdapter = configAdapterFactory.getAdapter();
        this.remoteSignatureProperties = remoteSignatureProperties;
    }

    public String getRemoteSignatureExternalDomain() {
        return configAdapter.getConfiguration(remoteSignatureProperties.externalDomain());
    }

    public String getRemoteSignatureInternalDomain() {
        return configAdapter.getConfiguration(remoteSignatureProperties.internalDomain());
    }

    public String getRemoteSignatureSignPath() {
        return configAdapter.getConfiguration(remoteSignatureProperties.paths().signPath());
    }
    
}
