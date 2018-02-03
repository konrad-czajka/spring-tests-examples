package konradczajka.springtestsexamples.archiving;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitMvcMockTest {
    @InjectMocks
    private ArchiveController controller;
    @Mock
    private ArchivingService service;

    private MockMvc mvc;

    @Before
    public void setupMvcMock() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void properlyCallsStoringOperation() throws Exception {
        when(service.storeFile(anyString(), anyString(), anyBoolean()))
                .thenReturn("target/path");

        mvc.perform(post("/archive/xstream")
                .contentType(APPLICATION_JSON)
                .content("{\"sourceFilePath\":\"source/path\",\"overwriteIfExists\":true}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.targetFilePath").value("target/path"));

        verify(service).storeFile("xstream", "source/path", true);
    }
}
