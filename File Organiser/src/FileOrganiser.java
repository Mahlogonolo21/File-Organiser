import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOrganiser {

    public static void main(String[] args) {
        
        String folderPath = "C:/Users/YourName/TestOrganiser";
        organiseFiles(folderPath);
    }

    public static void organiseFiles(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists()) {
            System.out.println("Folder does not exist!");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found!");
            return;
        }

        int total = files.length;
        int count = 0;

        for (File file : files) {
            if (file.isFile()) {
                String ext = getExtension(file.getName());
                String category = switch (ext) {
                    case "jpg", "png", "gif" -> "Images";
                    case "pdf", "docx", "txt" -> "Documents";
                    case "mp4", "avi" -> "Videos";
                    case "mp3", "wav" -> "Audio";
                    default -> "Others";
                };

                moveFile(file, folderPath + "/" + category);

                // Update progress bar
                count++;
                showProgress(count, total);
            }
        }

        System.out.println("\n✨ All tidy! Keep your workspace clean and creative! ✨");
    }

    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static void moveFile(File file, String targetDir) {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path targetPath = new File(dir, file.getName()).toPath();
        try {
            Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved " + file.getName() + " → " + targetDir);
        } catch (IOException e) {
            System.out.println("Could not move " + file.getName());
        }
    }

    // Simple progress bar
    public static void showProgress(int done, int total) {
        int percent = (int) ((done * 100.0f) / total);
        StringBuilder bar = new StringBuilder("[");
        int ticks = percent / 10;
        for (int i = 0; i < 10; i++) {
            if (i < ticks) bar.append("█");
            else bar.append(" ");
        }
        bar.append("] ").append(percent).append("%");
        System.out.println(bar);
    }
}

