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
        String token = "Nzc1MjA4MzE3NDgzMDg5OTYw.X6i_AQ.5xvNL9YT5TxvKyfrVP8QwIi1wiY";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.updateActivity("\"-도움말\"이라고 해봐");

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
            String userMention = message.getUserAuthor().get().getMentionTag();

            embed.setColor( Color.CYAN );

            if( !msg.startsWith("-") ) return;

            if( msg.contains("핑") )
                ch.sendMessage("안알랴줌");
            else if( msg.contains("굴러") )
                ch.sendMessage("데구르르 데굴데굴데굴");
            else if ( msg.contains("도움말") ) {
                replaced = msg.replace( "-도움말 ", "" );

                if( replaced.equals("기능") ){
                    embed.setTitle("기능");
                }
                else if ( replaced.equals("나무심기") ){
                    embed.setTitle("나무심기")
                         .setDescription("나무심기 게임에 관한 설명입니다.\n처음이라면 `-등록` 을 입력해 등록을 진행해주세요.")
                         .addField("`-나무 가방`", "현재 자신의 소지품 목록을 보여줍니다.")
                         .addField("`-나무 지갑`", "현재 자신의 소지금과 소지금 랭킹을 보여줍니다.")
                         .addField("`-나무 현황`", "현재 자신이 심은 나무의 현황을 보여주고 관리합니다.")
                         .addField("`-나무 심기`", "보유한 씨앗을 심습니다. 나무 한 그루는 자리 1을 차지합니다.")
                         .addField("`-나무 베기`", "다 자란 나무를 벱니다. 벤 나무는 목재가 되고 사라집니다.")
                         .addField("`-나무 제작`", "보유한 목재로 물건을 만듭니다.\n만든 물건은 가치가 매겨져 팔 수 있습니다.")
                         .addField("`-나무 팔기`", "만든 물건을 팝니다.\n물건을 팔아서 돈을 얻습니다.")
                         .addField("`-나무 상점`", "상점 목록을 보여줍니다. 씨앗이나 기념품을 구매할 수 있습니다.");
                }
                else {
                    embed.setTitle("에이마이너스 도움말")
                         .setDescription("에이마이너스는 미니게임과 각종 기능을 이용할수 있는 봇입니다.")
                         .addField("-[커맨드]", "아래의 명령어들은 모두 \"-\"를 앞에 붙혀서 불러야 정상적으로 처리됩니다.")
                         .addField("도움말", "지금 보고 있는 도움말을 보여줍니다.")
                         .addField("도움말 나무심기", "나무심기 게임에 대한 도움말을 보여줍니다.")
                         .addField("굴러", "데굴데굴 구릅니다.");
                }

                ev.getChannel().sendMessage( userMention, embed );
            }
            else if( msg.endsWith("등록") ){
                embed.setTitle("등록하기");
                embed.setDescription("`-등록 [닉네임]` 으로 등록을 진행해주세요.\n`[닉네임]`에 지정한 이름은 봇의 게임 내에서 이용됩니다.");

                ev.getChannel().sendMessage( userMention, embed );
            }
            else if( msg.contains("등록") && !msg.endsWith("등록") ) {
                try {
                    String nickname = msg.replace("-등록 ", "");

                    ev.getChannel().sendMessage(userMention + nickname + "(으)로 등록이 완료되었습니다!");
                    ev.getChannel().sendMessage("등록이 완료됐어. 앞으로 잘 부탁해!");
                } catch (Exception e) {
                    e.printStackTrace();
                    ch.sendMessage("오류가 발생했어! 등록 양식을 다시 확인해줘.");
                }
            }
            else if( msg.contains("나무") ){
                plantTree();
            }
            else if( msg.contains("이미지") ) {
                embed.setImage("https://blog.hubspot.com/hubfs/giphy_1-1.gif");
                ch.sendMessage( embed );
            }
            else if( msg.contains("에이") )
                ch.sendMessage("메모장 좀 그만 쓰면 좋겠는데..");
            else if( msg.contains("테스트") ){
                DBManager db = new DBManager();
                ch.sendMessage("제대로 출력되었는지 콘솔창을 확인해줘.");
            }
            else
                ch.sendMessage("그런 명령어는 아무리 뒤져봐도 없는 것 같은데..");
        } );
        printLOG("초대링크: " + api.createBotInvite());
    }
    public static void plantTree(){

    }
    public static void printLOG( String content ){
        SimpleDateFormat formatNow = new SimpleDateFormat ("yyyy.MM.dd(E) ahh:mm:ss Zz");
        Date timeNow = new Date();
        String tmNow= formatNow.format(timeNow);

        System.out.println("[LOG]" + "[" + tmNow + "] " + content);
    }
}