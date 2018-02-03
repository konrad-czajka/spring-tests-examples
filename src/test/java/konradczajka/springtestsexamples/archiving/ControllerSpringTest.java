package konradczajka.springtestsexamples.archiving;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ArchiveController.class)
public class ControllerSpringTest {
    @MockBean
    private ArchivingService service;

    @Autowired
    private MockMvc mvc;

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
