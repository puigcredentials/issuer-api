package es.puig.issuer.domain.service;

import es.puig.issuer.domain.model.dto.VcTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CredentialSchemaService {
    Mono<Boolean> isSupportedVcSchema(String credentialType);

    Mono<List<VcTemplate>> getAllVcTemplates();

    Mono<List<VcTemplate>> getAllDetailedVcTemplates();

    Mono<VcTemplate> getTemplate(String templateName);
}
