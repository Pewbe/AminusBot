import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AminusBot_Main {
    public static void main( String[] args ){
        String token = "Nzc1MjA4MzE3NDgzMDg5OTYw.X6i_AQ.rHMcprOwARPCOML9tWolfurJj3o";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.updateActivity("\"-도움말\"이라고 해보는거야");

        api.addServerJoinListener( ev ->{
            Server server = ev.getServer();
            printLOG( server.getName() + " 에 초대된거야" );
        } );

        api.addMessageCreateListener( ev -> {
            TextChannel ch = ev.getChannel();
            Message message = ev.getMessage();
            String msg = ev.getMessageContent();
            EmbedBuilder embed = new EmbedBuilder();
            String replaced, splitd, buff;//임시 스트링 변수들

            embed.setColor( Color.CYAN );

            if( !msg.startsWith("-") ) return;

            if( msg.contains("핑") )
                ch.sendMessage("안알랴줌");
            else if( msg.contains("굴러") )
                ch.sendMessage("데구르르 데굴데굴데굴");
            else if( msg.contains("도움말") ){
                embed.setTitle("에이마이너스 도움말")
                        .setDescription("에이마이너스는 미니게임과 각종 기능을 이용할수 있는 봇입니다.")
                        .addField("-[커맨드]", "아래의 명령어들은 \"-\"를 앞에 붙혀서 불러야 잘 알아들을수 있습니다.")
                        .addField("도움말", "지금 보고 있는 도움말을 보여줍니다.")
                        .addField("굴러", "데굴데굴 구릅니다.");
                ch.sendMessage( embed );
            } else if( msg.contains("삭제") ) {
                int amount = Integer.parseInt(msg.replace("-삭제 ", ""));
            } else if( msg.contains("에이") )
                ch.sendMessage("나보다 똑똑하긴 하지만 구식인걸");
            else if( msg.contains("테스트") ){
                DBManager db = new DBManager();
                ch.sendMessage("제대로 출력되었는지 콘솔창을 확인해줘.");
            }
            else
                ch.sendMessage("그런 명령어는 아무리 뒤져봐도 없는 것 같은데..");
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