import java.io.File;

/**
 * The FileHandler class provides methods to handle file operations.
 * It allows setting a file input and retrieving it.
 */
public class FileHandler {
  private File fileInput;

  /**
   * Default constructor for FileHandler.
   */
  public FileHandler() {
    // Default Constructor
  }

  /**
   * Sets the file input using the specified file path.
   *
   * @param filePath the path of the file to be set as input
   */
  public void readFile(String filePath) {
    this.fileInput = new File(filePath);
  }

  /**
   * Retrieves the current file input.
   *
   * @return the current file input
   */
  public File readFile() {
    return this.fileInput;
  }
}
