package konradczajka.springtestsexamples.archiving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/archive")
@AllArgsConstructor
public class ArchiveController {
    private ArchivingService archivingService;

    @PostMapping("/{tenant}")
    @ResponseStatus(CREATED)
    ArchivingResult archiveFile(@PathVariable String tenant, @RequestBody @Valid ArchivingRequest request) {

        String archivedFilePath = archivingService.storeFile(tenant, request.getSourceFilePath(), request.overwriteIfExists);

        return new ArchivingResult(archivedFilePath);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ArchivingRequest {
        @NotNull
        private String sourceFilePath;
        private boolean overwriteIfExists = false;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ArchivingResult {
        private String targetFilePath;
    }
}
