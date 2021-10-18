import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {


    private static String SITE_URL = "https://lenta.ru/";  //"https://skillbox.ru/";//"https://lenta.ru/";
    private static final String SITEMAP_DOCUMENT_PATH = "src/main/resources/MapOfSite.txt";


    public static void main(String [] args){
        Set<String> allLinks = new HashSet<>();

        RecursiveSiteMapNode linkSetExecutor = new RecursiveSiteMapNode(SITE_URL, allLinks);
        String siteMap = new ForkJoinPool().invoke(linkSetExecutor);
        writeFiles(siteMap);
    }

    public static void writeFiles(String map) {

        File file = new File(SITEMAP_DOCUMENT_PATH);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
