import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "/users/Pavel/Desktop/src";
        String dstFolder = "/users/Pavel/Desktop/dst";

        int cores = Runtime.getRuntime().availableProcessors();
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        assert files != null;
        List<File[]> filesList = getListFiles(files, cores);

        long start = System.currentTimeMillis();
        for(File[] filesPart : filesList) {
            new Thread(new ImageResizer(filesPart, newWidth, dstFolder, start)).start();
        }

    }

    public static List<File[]> getListFiles(File[] files, int cores) {

        List<File[]> filesList = new ArrayList<>();
        int part = files.length/cores;

        for(int i = 0; i < cores; i++)
        {
            if(i == cores - 1)
            {
                int sizeLast = part + (files.length - (part*cores));
                File[] files1 = new File[sizeLast];
                System.arraycopy(files, part * i, files1, 0, sizeLast);
                filesList.add(files1);
            }else {
                File[] files1 = new File[part];
                System.arraycopy(files, part * i, files1, 0, part);
                filesList.add(files1);
            }
        }

        return filesList;
    }
}
