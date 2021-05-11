package forum.modules.ranking;

import forum.database.ForumMember;
import java.util.Date;
import java.util.HashMap;

public class RankManager {
    public static Date last;
    protected static HashMap<Integer, ForumMember> lastRanking;
    protected static HashMap<ForumMember,Integer> reverseLastRanking;

    public static HashMap<Integer, ForumMember> getRanking(){
        if(last==null){
            last = new Date();
            new Ranking();
        }

        return lastRanking; }

    public static int getMembersRank(ForumMember member){
        if(last==null){
            getRanking();
        }
        return reverseLastRanking.get(member);
    }
}
