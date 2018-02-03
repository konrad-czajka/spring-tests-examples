package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.filesystem.FileManager;
import konradczajka.springtestsexamples.filesystem.S3FileManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"archiving.type = s3"})
@Import(FileManagerConfiguration.class)
public class ConfigurationSpringTestWithAutowiredBean {

    @Autowired
    private FileManager fileManager;

    @Test
    public void createsS3ManagerWhenItWasConfigured() {
        assertThat(fileManager, instanceOf(S3FileManager.class));
    }
}
