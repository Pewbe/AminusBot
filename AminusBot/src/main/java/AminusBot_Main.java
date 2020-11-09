import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AminusBot_Main {
    public static void main( String[] args ){
        String token = "Nzc1MjA4MzE3NDgzMDg5OTYw.X6i_AQ.zhwNeSsxHof6l5PbMe9VE4BaWpM";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener( ev -> {
            TextChannel ch = ev.getChannel();
            String msg = ev.getMessageContent();

            if( !msg.startsWith("-") ) return;

            if( msg.contains("핑") ){
                ch.sendMessage("퐁!");
            }
            else if( msg.contains("굴러") ){
                ch.sendMessage("데굴데굴데굴......");
            } else {
                ch.sendMessage("그런거 모르는걸");
            }
        } );
        printLOG("초대링크: " + api.createBotInvite());
    }
    public static void printLOG( String content ){
        SimpleDateFormat formatNow = new SimpleDateFormat ("yyyy.MM.dd(E) ahh:mm:ss Zz");
        Date timeNow = new Date();
        String tmNow= formatNow.format(timeNow);

        System.out.println("[LOG]" + "[" + tmNow + "] " + content);
    }
}