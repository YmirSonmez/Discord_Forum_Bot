package forum.database;

import forum.modules.ranking.RankManager;

public class ForumMember {
    private final String userID;
    private int xp;
    private int threadCount;

    public ForumMember(String userID, int xp, int threadCount) {
        this.userID = userID;
        this.xp = xp;
        this.threadCount = threadCount;
    }

    public String getUserID() {
        return userID;
    }

    public int getXp() {
        return xp;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public ForumMember setXp(int xp) {
        this.xp = xp;
        return this;
    }

    public ForumMember setThreadCount(int threadCount) {
        DataBase.totalThread += (threadCount-this.threadCount);
        this.threadCount = threadCount;
        return this;
    }

    public ForumMember addXp(int xpp){
        this.xp += xpp;
        return this;
    }
    public ForumMember addThread(int threadCountt){
        DataBase.totalThread +=threadCountt;
        this.threadCount += threadCountt;
        return this;
    }

    public void complete(){
        DataBase.update(this.userID,this.xp,this.threadCount);
        RankManager.last = null;
    }

    public String toString(){
        return "<@"+this.userID+">"
                +" **xp: **"+ this.xp
                +"** konu sayısı: **"+ this.threadCount;
    }

}
