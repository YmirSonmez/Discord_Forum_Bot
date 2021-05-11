package forum.setting;


import com.electronwill.nightconfig.core.file.FileConfig;
import forum.Main;


public class Config {
    private static final FileConfig config = FileConfig.of(Main.path+"resources/Config.yaml");
    private static final String[] requiredSettings=new String[]{"GUILDID","BOTCHANNELID","CONFIRMCHANNELID","MAINCHANNELID","ARCHIVECHANNELID","CATEGORYID","MEMBERROLEID","WRITEBANNEDROLEID","FORUMBANNEDROLEID","ADMINROLEID","PREFIX","INVITELINK","TOKEN","DBLOCATION"};

    public static String get(String key){
        return config.get(key).toString();
    }
    public static void checkFile(){
        config.load();
        for(String key:requiredSettings){
            if(get(key)==null){
                System.out.println(key+" bulunamadı!");
                System.exit(0);
            }
        }
    }
    public static void setConfig(String key,String value){
        config.set(key,value);
    }
    public static void saveConfig(){
        config.save();
    }
}
