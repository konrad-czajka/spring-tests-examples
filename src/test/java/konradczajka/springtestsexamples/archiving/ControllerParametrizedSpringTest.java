package konradczajka.springtestsexamples.archiving;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(Parameterized.class)
@WebMvcTest(controllers = ArchiveController.class)
@MockBean(ArchivingService.class)
public class ControllerParametrizedSpringTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private MockMvc mvc;

    @Parameter(0)
    public String requestBody;
    @Parameter(1)
    public HttpStatus expectedResponseStatus;


    // (request body, expected response status) pairs
    @Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"{\"sourceFilePath\":\"source/path\",\"overwriteIfExists\":true}", CREATED},
                {"{\"sourceFilePath\":\"source/path\"}", CREATED},
                {"{}", BAD_REQUEST},
                {"abc", BAD_REQUEST},
                {"", BAD_REQUEST}
        });
    }

    @Test
    public void properlyValidatesRequestBody() throws Exception {
        mvc.perform(post("/archive/xstream")
                .contentType(APPLICATION_JSON)
                .content(requestBody))
           .andExpect(status().is(expectedResponseStatus.value()));;
    }
}
