package org.statistical.analysis.tasks;

import java.util.concurrent.ThreadFactory;

public class FindNGramsThreadFactory implements ThreadFactory {
    private int counter = 0;
    private String prefix = "";

    public FindNGramsThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
    }

}
