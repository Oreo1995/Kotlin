import java.io.File;
import java.util.List;

//this interface can write by kotlin in ch06
public interface FileContentProcessor {
    void processContents(File path,
                         byte[] binaryContents,
                         List<String> textContents);
}
