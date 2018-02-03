package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.filesystem.FileManager;
import konradczajka.springtestsexamples.filesystem.S3FileManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"archiving.type = s3"})
@Import(FileManagerConfiguration.class)
public class ConfigurationSpringTestWithContextVerification {

    @Autowired
    private ApplicationContext context;

    @Test
    public void createsS3ManagerWhenItWasConfigured() {
        assertTrue(context.containsBean("fileManager"));

        FileManager fileManager = context.getBean(FileManager.class);
        assertThat(fileManager, instanceOf(S3FileManager.class));
    }
}
