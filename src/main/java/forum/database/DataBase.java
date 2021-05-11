package forum.database;


import forum.modules.ranking.RankManager;
import forum.setting.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    public static int totalThread;
    public static final HashMap<String,ForumMember> memberList = new HashMap<>();
    private static Connection conn=null;
    private static final String url= "jdbc:sqlite:"+ Config.get("DBLOCATION");
    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("DataBase connected.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<ForumMember> getMemberList(){
        List<ForumMember> list = new ArrayList<>();
        String sql = "select * from member";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                list.add(new ForumMember(rs.getString("userID"),rs.getInt("xp"),rs.getInt("threadcount")));
            }
            st.close();
        } catch (SQLException throwable) {
            list.add(new ForumMember("0000000000",0,0));
        }

        return list;
    }

    public static void update(String id, int xp, int count) {
        String sql = "UPDATE member SET xp = ? , "
                + "threadcount = ? "
                + "WHERE userID = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,xp);
            preparedStatement.setInt(2,count);
            preparedStatement.setString(3,id);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void add(String id, int xp, int count){
        String sql = "INSERT INTO member(userID,xp,threadcount) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setInt(2,xp);
            pstmt.setInt(3,count);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void delete(String id){
        String sql = "DELETE FROM member WHERE userID = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
            memberList.remove(id);
            RankManager.last = null;
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static boolean checkMember(String id){
        return memberList.containsKey(id);
    }
    public static ForumMember getMember(String id){
        if(!memberList.containsKey(id)){
            return addMember(id);
        }
        return memberList.get(id);
    }
    public static ForumMember addMember(String id){
        ForumMember member = new ForumMember(id,0,0);
        memberList.put(id,member);
        add(id,0,0);
        RankManager.last=null;
        return member;
    }

    public static int getTotalThread(){
        return totalThread;
    }

}
