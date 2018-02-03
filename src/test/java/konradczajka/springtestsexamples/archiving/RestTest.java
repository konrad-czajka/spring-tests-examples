package konradczajka.springtestsexamples.archiving;

import konradczajka.springtestsexamples.externalapi.ExtApiClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest
@ComponentScan("konradczajka.springtestsexamples.externalapi")
@TestPropertySource(properties = "ext.api.url = http://test.api")
public class RestTest {
    @Autowired
    private ExtApiClient client;

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    @Before
    public void configureService() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void fetchesDataByIdFromApi() {
        server.expect(/*times(1),*/ requestTo("http://test.api/data/5"))
              .andRespond(withSuccess("data-five", TEXT_PLAIN));

        String result = client.fetchData(5);

        assertThat(result, is("result: data-five"));
        server.verify();
    }
}
