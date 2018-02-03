package konradczajka.springtestsexamples.archiving.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ConfigurationProperties("archiving")
@Getter
@Setter
public class ArchivingProperties {

    private StorageType type;

    private Map<String, TenantStorageConfig> storages = new HashMap<>();

    public TenantStorageConfig getArchivingConfig(String tenant) {
        return Optional.ofNullable(storages.get(tenant))
                       .orElseThrow(IllegalArgumentException::new);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TenantStorageConfig {
        private String host;
        private String username;
        private String password;
    }

}

