package konradczajka.springtestsexamples.archiving;

import konradczajka.springtestsexamples.archiving.ArchiveController.ArchivingRequest;
import konradczajka.springtestsexamples.archiving.ArchiveController.ArchivingResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {
    @InjectMocks
    private ArchiveController controller;
    @Mock
    private ArchivingService service;

    @Test
    public void properlyCallsStoringOperation() {
        when(service.storeFile(anyString(), anyString(), anyBoolean()))
                .thenReturn("target/path");

        ArchivingRequest request = new ArchivingRequest("source/path", true);
        ArchivingResult result = controller.archiveFile("xstream", request);

        assertThat(result.getTargetFilePath(), is("target/path"));
        verify(service).storeFile("xstream", "source/path", true);
    }
}
