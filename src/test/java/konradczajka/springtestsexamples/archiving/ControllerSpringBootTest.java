package konradczajka.springtestsexamples.archiving;

import konradczajka.springtestsexamples.filesystem.FileManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class ControllerSpringBootTest {
    @MockBean
    private FileManager fileManager;

    // @SpyBean
    // private ArchivingService service;

    @Autowired
    private MockMvc mvc;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Test
    public void properlyCallsStoringOperation() throws Exception {
        when(fileManager.exists(anyString()))
                .thenReturn(true);

        String expectedTargetPath = "127.1.2.3/file.txt";

        mvc.perform(post("/archive/xstream")
                .contentType(APPLICATION_JSON)
                .content("{\"sourceFilePath\":\"source/file.txt\",\"overwriteIfExists\":true}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.targetFilePath").value(expectedTargetPath));

        verify(fileManager).copy("source/file.txt", expectedTargetPath);
    }
}
