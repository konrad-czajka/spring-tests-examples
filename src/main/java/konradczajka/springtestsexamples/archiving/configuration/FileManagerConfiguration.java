package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.filesystem.FileManager;
import konradczajka.springtestsexamples.filesystem.FtpFileManager;
import konradczajka.springtestsexamples.filesystem.S3FileManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ArchivingProperties.class)
@AllArgsConstructor
class FileManagerConfiguration {

    private ArchivingProperties archivingProperties;

    @Bean
    FileManager fileManager() {
        switch (archivingProperties.getType()) {
            case S3:
                return new S3FileManager();
            case FTP:
                return new FtpFileManager();
            default:
                throw new IllegalArgumentException("Unknown storage type: " + archivingProperties.getType());
        }
    }
}
