package forum.embed;

import forum.utils.ThreadContext;

public class EmbedPages {
    private final ThreadContext context;
    private int currentPageNumber;

    public EmbedPages(ThreadContext context, int currentPageNumber) {
        this.context = context;
        this.currentPageNumber = currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber){this.currentPageNumber=currentPageNumber;}
    public ThreadContext getContext(){return this.context;}
    public int getCurrentPageNumber(){return this.currentPageNumber;}


}