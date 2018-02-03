package konradczajka.springtestsexamples.archiving;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ArchiveItemsRepository extends Repository<ArchiveItem, Long> {

    Optional<ArchiveItem> findByTenantAndPath(String tenant, String path);

    List<ArchiveItem> findAllByPathStartingWith(String pathPrefix);

    @Query("SELECT i.path FROM ArchiveItem i WHERE MOD(i.id, 7) = 0")
    List<String> findAllPathsWithIdDivisibleBySeven();
}
