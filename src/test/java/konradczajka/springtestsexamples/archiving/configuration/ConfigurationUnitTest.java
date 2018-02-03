package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.filesystem.FileManager;
import konradczajka.springtestsexamples.filesystem.S3FileManager;
import org.junit.Before;
import org.junit.Test;

import static konradczajka.springtestsexamples.archiving.configuration.StorageType.PIGEON;
import static konradczajka.springtestsexamples.archiving.configuration.StorageType.S3;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ConfigurationUnitTest {
    private ArchivingProperties properties;
    private FileManagerConfiguration configuration;

    @Before
    public void setup() {
        properties = new ArchivingProperties();
        configuration = new FileManagerConfiguration(properties);
    }

    @Test
    public void createsS3ManagerWhenItWasConfigured() {
        properties.setType(S3);

        FileManager fileManager = configuration.fileManager();

        assertThat(fileManager, instanceOf(S3FileManager.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenGivenStorageTypeIsNotSupported() {
        properties.setType(PIGEON);

        configuration.fileManager();
    }
}
