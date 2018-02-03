package konradczajka.springtestsexamples.filesystem;

public class S3FileManager implements FileManager {
    @Override
    public boolean exists(String path) {
        // do stuff
        return false;
    }

    @Override
    public void copy(String source, String target) {
        // do stuff
    }
}
