package konradczajka.springtestsexamples.archiving;

import konradczajka.springtestsexamples.archiving.configuration.ArchivingProperties;
import konradczajka.springtestsexamples.filesystem.FileManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArchivingService {
    private FileManager fileManager;
    private ArchivingProperties archivingProperties;

    public String storeFile(String tenant, String sourcePath, boolean overwriteIfExists) {
        ArchivingProperties.TenantStorageConfig archivingConfig = archivingProperties.getArchivingConfig(tenant);
        String targetPath = archivingConfig.getHost() + sourcePath.substring(sourcePath.lastIndexOf("/"));

        if (!overwriteIfExists && fileManager.exists(targetPath)) {
            throw new IllegalStateException("Target file already exists");
        }

        fileManager.copy(sourcePath, targetPath);

        return targetPath;
    }
}
