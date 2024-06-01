package es.puig.issuer.infrastructure.config.adapter.impl;

import es.puig.issuer.infrastructure.config.adapter.ConfigAdapter;
import org.springframework.stereotype.Component;

@Component
public class YamlConfigAdapter implements ConfigAdapter {

    @Override
    public String getConfiguration(String key){
        return key;
    }

}
