package forum.modules.ranking;

import forum.database.DataBase;
import forum.database.ForumMember;
import java.util.HashMap;


public class Ranking {
    private final HashMap<Integer, ForumMember> lastRanking = new HashMap<>();
    private final HashMap<String, ForumMember> memberList;
    private final HashMap<ForumMember,Integer> reverseLastRanking= new HashMap<>();

    public Ranking() {
        this.memberList= DataBase.memberList;
        getRanking();
    }

    private void getRanking(){
        int count = this.memberList.size();
        for(ForumMember member:this.memberList.values()) {
            int rank = 0;
            for(ForumMember anotherMember:this.memberList.values()){
                if(!member.equals(anotherMember)&&member.getXp()>anotherMember.getXp()){
                    ++rank;
                }else if(!member.equals(anotherMember)&&this.lastRanking.get((count-rank))!=null){
                    ++rank;
                }
            }
            this.lastRanking.put((count-rank),member);
            this.reverseLastRanking.put(member,(count-rank));
        }
        RankManager.lastRanking=this.lastRanking;
        RankManager.reverseLastRanking=this.reverseLastRanking;
    }


    public HashMap<Integer, ForumMember> getRankingList(){return this.lastRanking;}
    public HashMap<ForumMember, Integer> getReverseRankingList(){return this.reverseLastRanking;}
}
