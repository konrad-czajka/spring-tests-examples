package konradczajka.springtestsexamples.archiving;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ArchiveItemsRepository repository;

    @Before
    public void loadFixtures() {
        entityManager.persist(new ArchiveItem(1L, "xstream", "path/to/image.png"));
        entityManager.persist(new ArchiveItem(7L, "xstream", "other/path/to/image2.png"));
        entityManager.persist(new ArchiveItem(8L, "other-tenant", "some/path/to/image3.png"));
        entityManager.persist(new ArchiveItem(14L, "other-tenant", "image4.png"));
    }

    @Test
    public void returnsExistingItemByTenantAndPath() {
        Optional<ArchiveItem> result = repository.findByTenantAndPath("xstream", "other/path/to/image2.png");

        assertTrue(result.isPresent());
        assertThat(result.get().getId(), equalTo(7L));
    }

    @Test
    public void findsAllItemsByPathPrefix() {
        List<ArchiveItem> result = repository.findAllByPathStartingWith("some/");

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getId(), equalTo(8L));
    }

    @Test
    public void findsAllItemsWithSpecialId() {
        List<String> result = repository.findAllPathsWithIdDivisibleBySeven();

        assertThat(result, Matchers.containsInAnyOrder("image4.png", "other/path/to/image2.png"));
    }
}
