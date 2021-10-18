import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.HashSet;

import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class RecursiveSiteMapNode extends RecursiveTask<String> {

    private String url;
    //private static CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();
    private Set<String> allLinks;
    private int parentSlashNumber;

    public RecursiveSiteMapNode(String url, Set<String> allLinks) {
        this.url = url;
        this.allLinks = allLinks;
        parentSlashNumber = 3;
    }

    public String getUrl() {
        return url;
    }

    @Override
    protected String compute() {

        StringBuilder stringBuilder = new StringBuilder(url + "\n");
        Set<RecursiveSiteMapNode> subTask = new HashSet<>();

        getChildrenLink(subTask);

        for (RecursiveSiteMapNode link : subTask)
        {
            int slashCount = link.getUrl().length() - link.getUrl().replace("/", "").length();
            int tabCount = slashCount - parentSlashNumber;

            for (int i = 0; i <= tabCount; i++)
            {
                stringBuilder.append("\t");
            }

            stringBuilder.append(link.join());
        }

        return stringBuilder.toString();
    }

    private void getChildrenLink(Set<RecursiveSiteMapNode> subTask) {
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                String attr = element.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(url) && !allLinks.contains(attr) && !attr
                        .contains("#") && !url.contains("pdf") && !attr.contains("pdf")) {
                    RecursiveSiteMapNode linkSetExecutor = new RecursiveSiteMapNode(attr, allLinks);
                    linkSetExecutor.fork();
                    subTask.add(linkSetExecutor);
                    allLinks.add(attr);
                }
            }
        } catch (InterruptedException | IOException exception) {
        }
    }
}
