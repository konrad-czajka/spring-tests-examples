package konradczajka.springtestsexamples.filesystem;

public interface FileManager {
    boolean exists(String path);
    void copy(String source, String target);
}
