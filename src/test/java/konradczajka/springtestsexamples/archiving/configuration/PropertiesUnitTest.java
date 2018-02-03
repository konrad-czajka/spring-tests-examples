package konradczajka.springtestsexamples.archiving.configuration;

import konradczajka.springtestsexamples.archiving.configuration.ArchivingProperties.TenantStorageConfig;
import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PropertiesUnitTest {

    @Test
    public void returnsConfigForValidTenant() {
        ArchivingProperties properties = new ArchivingProperties();
        properties.setStorages(singletonMap("xstream", new TenantStorageConfig("127.1.2.3", "root", "root123")));

        TenantStorageConfig result = properties.getArchivingConfig("xstream");

        assertThat(result.getHost(), is("127.1.2.3"));
        assertThat(result.getUsername(), is("root"));
        assertThat(result.getPassword(), is("root123"));

        // or, as TenantStorageConfig implements equals(), we can rewrite assertions as:
        // TenantStorageConfig expectedConfig = new TenantStorageConfig("127.1.2.3", "root", "root123");
        // assertThat(result, equalTo(expectedConfig));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenTenantIsNotConfigured() {
        ArchivingProperties properties = new ArchivingProperties();

        properties.getArchivingConfig("xstream");
    }
}
