package exercise4;

import common.StringUtils;

import java.util.concurrent.RecursiveTask;

public class WordCountingTask extends RecursiveTask<Integer> {

    private final String content;
    private final String wordToCount;

    public WordCountingTask(String content, String wordToCount) {
        this.content = content.toLowerCase();
        this.wordToCount = wordToCount;
    }

    @Override
    protected Integer compute() {
        return StringUtils.countOccurrences(content, wordToCount);
    }
}
