package exercise4;

import common.html.GazetaHtmlDocument;
import common.html.HtmlDocument;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class WebCrawlingTask extends RecursiveTask<Integer> {

    private static final Set<String> visitedLinks = new HashSet<String>();

    private final String documentUrl;
    private final String wordToCount;

    public WebCrawlingTask(String documentUrl, String wordToCount) {
        this.documentUrl = documentUrl;
        this.wordToCount = wordToCount;
    }

    @Override
    protected Integer compute() {
        System.out.printf("Looking for words '%s' in article %s\n", wordToCount, documentUrl);
        HtmlDocument document = new GazetaHtmlDocument(documentUrl);

        List<RecursiveTask<Integer>> forks = new LinkedList<RecursiveTask<Integer>>();
        WordCountingTask wordCountingTask = new WordCountingTask(document.getContent(), wordToCount);
        forks.add(wordCountingTask);
        wordCountingTask.fork();

        for (String link : document.getLinks()) {
            // Avoid visiting the same links and limit number of visited links
            boolean visited = true;
            synchronized (visitedLinks) {
                if (visitedLinks.size() > 50) {
                    break;
                }
                if (!visitedLinks.contains(link)) {
                    visited = false;
                    visitedLinks.add(link);
                }
            }

            if (!visited) {
                WebCrawlingTask webCrawlingTask = new WebCrawlingTask(link, wordToCount);
                forks.add(webCrawlingTask);
                webCrawlingTask.fork();
            }
        }

        int result = 0;
        for (RecursiveTask<Integer> fork : forks) {
            result += fork.join();
        }
        return result;
    }
}
