import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {

    private final File[] files;
    private final int newWidth;
    private final String dst;
    private final long start;

    public ImageResizer(File[] files, int newWidth, String dst, long start){

        this.files = files;
        this.newWidth = newWidth;
        this.dst = dst;
        this.start = start;
    }
    @Override
    public void run() {
        try {
            for(File file : files) {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }
                int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));
                BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);
                File newFile = new File(dst + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + " ms.");
    }

    }


