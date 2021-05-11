package forum.gui;


import forum.database.DataBase;
import forum.listeners.GenericListener;
import forum.setting.Config;
import forum.setting.Setting;
import forum.utils.BotUtils;
import net.dv8tion.jda.api.OnlineStatus;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ForumMenu extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTextField BotChannel;
    private JTextField Guild;
    private JTextField Confirm;
    private JTextField Main;
    private JTextField Category;
    private JTextField MemberRole;
    private JTextField Wbanned;
    private JTextField FBanned;
    private JTextField Admin;
    private JTextField Prefix;
    private JTextField Archive;
    private JTextField token;
    private JTextField Invite;
    private JButton updateButton;
    private JCheckBox runCheckBox;
    private JTextField Status;
    private JButton StatusSend;
    private JTextField DBLOCATION;
    private JTextArea consol;


    public ForumMenu(){
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        setVisible(true);
        setTitle("Jupiter Forum");
        Guild.setText(Config.get("GUILDID"));
        Confirm.setText(Config.get("CONFIRMCHANNELID"));
        BotChannel.setText(Config.get("BOTCHANNELID"));
        Main.setText(Config.get("MAINCHANNELID"));
        Category.setText(Config.get("CATEGORYID"));
        MemberRole.setText(Config.get("MEMBERROLEID"));
        Wbanned.setText(Config.get("WRITEBANNEDROLEID"));
        FBanned.setText(Config.get("FORUMBANNEDROLEID"));
        Admin.setText(Config.get("ADMINROLEID"));
        Prefix.setText(Config.get("PREFIX"));
        Archive.setText(Config.get("ARCHIVECHANNELID"));
        token.setText(Config.get("TOKEN"));
        Invite.setText(Config.get("INVITELINK"));
        DBLOCATION.setText(Config.get("DBLOCATION"));


        add(tabbedPane1);
        setIconImage(new ImageIcon(forum.Main.path+"resources/jupiterlogopng.png").getImage());

        PrintStream out = new PrintStream(new ByteArrayOutputStream() {
            public synchronized void flush(){
                consol.setText(toString());
            }
        }, true);

        System.setErr(out);
        System.setOut(out);

        //Bot başlatıcı
        runCheckBox.addItemListener(e -> {
            if(runCheckBox.isSelected()){
                try {
                    DataBase.connect();
                    forum.Main.Bot(Setting.TOKEN());
                } catch (LoginException loginException) {
                    loginException.printStackTrace();
                }

            }else {
                GenericListener.jda.shutdownNow();
            }
        });
        updateButton.addActionListener(e -> {
            Config.setConfig("GUILDID",Guild.getText());
            Config.setConfig("CONFIRMCHANNELID",Confirm.getText());
            Config.setConfig("BOTCHANNELID",BotChannel.getText());
            Config.setConfig("MAINCHANNELID",Main.getText());
            Config.setConfig("CATEGORYID",Category.getText());
            Config.setConfig("MEMBERROLEID",MemberRole.getText());
            Config.setConfig("WRITEBANNEDROLEID",Wbanned.getText());
            Config.setConfig("FORUMBANNEDROLEID",FBanned.getText());
            Config.setConfig("ADMINROLEID",Admin.getText());
            Config.setConfig("PREFIX",Prefix.getText());
            Config.setConfig("ARCHIVECHANNELID",Archive.getText());
            Config.setConfig("TOKEN",token.getText());
            Config.setConfig("INVITELINK",Invite.getText());
            Config.setConfig("DBLOCATION",DBLOCATION.getText());
            Config.saveConfig();
        });
        StatusSend.addActionListener(e -> {
            if(runCheckBox.isSelected()){
                BotUtils.setStatus(GenericListener.jda, OnlineStatus.IDLE,Status.getText());
            }else{
                JOptionPane.showMessageDialog(null, "Bot aktif değil!", "Hata!" , JOptionPane.ERROR_MESSAGE);
            }

        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
