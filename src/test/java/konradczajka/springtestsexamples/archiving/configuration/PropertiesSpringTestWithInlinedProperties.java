package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.archiving.configuration.ArchivingProperties.TenantStorageConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static konradczajka.springtestsexamples.archiving.configuration.StorageType.S3;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "archiving.type = s3",
        "archiving.storages.xstream.host     = 127.1.2.3",
        "archiving.storages.xstream.username : root",
        "archiving.storages.xstream.password   root123"
})
public class PropertiesSpringTestWithInlinedProperties {

    @Autowired
    private ArchivingProperties properties;

    @Test
    public void returnsConfigForValidTenant() {
        assertThat(properties.getType(), is(S3));

        TenantStorageConfig xstreamConfig = properties.getArchivingConfig("xstream");

        assertThat(xstreamConfig.getHost(), is("127.1.2.3"));
        assertThat(xstreamConfig.getUsername(), is("root"));
        assertThat(xstreamConfig.getPassword(), is("root123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenTenantIsNotConfigured() {
        properties.getArchivingConfig("not-a-tenant");
    }

    @Configuration
    @EnableConfigurationProperties(ArchivingProperties.class)
    static class TestConfig {
    }
}
