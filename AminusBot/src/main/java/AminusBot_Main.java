import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AminusBot_Main {
    public static void main( String[] args ) throws IOException, ExecutionException, InterruptedException {
        String token = "Nzc1MjA4MzE3NDgzMDg5OTYw.X6i_AQ.FYc8SD5rfwkTw3qZ1AH6kAQiCR4";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        DBManager manager = new DBManager();

        printLOG("디스코드에 로그인했습니다.");
        api.updateActivity("-도움말");

        /*
        api.getUserById("682556804927979523").get().sendMessage("🎉<@" + "682556804927979523" + ">" + "님, 2021년 새해가 밝았어요! 올해도 항상 좋은 일만 일어나시길 바랄게요!🎉");
        api.getUserById("668072089302990908").get().sendMessage("🎉<@" + "668072089302990908" + ">" + "님, 2021년 새해가 밝았어요! 올해도 항상 좋은 일만 일어나시길 바랄게요!🎉");
        api.getUserById("360435715781165067").get().sendMessage("🎉<@" + "360435715781165067" + ">" + "님, 2021년 새해가 밝았어요! 올해도 항상 좋은 일만 일어나시길 바랄게요!🎉");
        api.getUserById("667738634597957634").get().sendMessage("🎉<@" + "667738634597957634" + ">" + "님, 2021년 새해가 밝았어요! 올해도 항상 좋은 일만 일어나시길 바랄게요!🎉");
        api.getUserById("551364781504987186").get().sendMessage("🎉<@" + "551364781504987186" + ">" + "님, 2021년 새해가 밝았어요! 올해도 항상 좋은 일만 일어나시길 바랄게요!🎉");
         */

        api.addServerJoinListener( ev ->{
            Server server = ev.getServer();
            printLOG( server.getName() + " 에 초대되었습니다." );
        } );

        api.addMessageCreateListener( ev -> {
            TextChannel ch = ev.getChannel();
            Message message = ev.getMessage();
            String msg = ev.getMessageContent();
            EmbedBuilder embed = new EmbedBuilder();
            String replaced, buff;//임시 스트링 변수들
            User auther = message.getUserAuthor().get();
            String userMention = auther.getMentionTag();
            String userName = auther.getName();
            String[] splitted;
            long userid = auther.getId();
            Profile profile = manager.getProfile(userid);
            Server server = !message.isPrivateMessage() ? message.getServer().get() : null;

            embed.setColor( Color.CYAN );

            if( !msg.startsWith("-") ) return;

            String msgstr = msg.replace("-", "");

            if( server == null )
                printLOG("[개인 메시지] 에서 [" + userName + "]: \"" + msg + "\"");
            else
                printLOG("[" + server.getName() + "] 에서 [" + message.getAuthor().getName() + "]: \"" + msg + "\"");

            if( msg.contains("핑") )
                ch.sendMessage("안알랴줌");
            else if( msgstr.equals("[커맨드]") )
                ch.sendMessage("..예?");
            else if( msg.contains("굴러") )
                ch.sendMessage("데구르르 데굴데굴데굴");
            else if ( msg.contains("도움말") || msg.contains("명령어") ) {
                replaced = msg.replace( "-도움말 ", "" );

                if( replaced.equals("기능") ){
                    embed.addField("`등록 [닉네임]`", "봇에 등록합니다. 등록하여야만 사용할 수 있는 기능들을\n이용할 수 있게 됩니다.")
                         .addField("`-돈내놔`", "돈을 줍니다.")
                         .addField("`-지갑`", "현재 자신의 소지금을 보여줍니다.")
                         .addField("`-프로필`", "자신의 프로필을 보여줍니다.")
                         .addField("`-골라 [값1] [값2]...`", "봇이 무언가를 대신 골라줍니다.\n띄어쓰기로 구분합니다.");
                }
                else if ( replaced.equals("나무심기") ){
                    embed.setTitle("나무심기")
                         .setDescription("나무심기 게임에 관한 설명입니다.\n처음이라면 `-등록` 을 입력해 등록을 진행해주세요.")
                         .addField("`-나무 가방`", "현재 자신의 소지품 목록을 보여줍니다.")
                         .addField("`-나무 현황`", "현재 자신이 심은 나무의 현황을 보여줍니다.")
                         .addField("`-나무 심기 [개수]`", "보유한 씨앗을 심습니다. 나무 한 그루는 자리 1을 차지합니다.")
                         .addField("`-나무 베기 [개수]`", "다 자란 나무를 벱니다. 벤 나무는 목재가 되고 사라집니다.")
                         .addField("`-나무 제작`", "보유한 목재로 물건을 만듭니다.\n만든 물건은 가치가 매겨져 팔 수 있습니다.")
                         .addField("`-나무 팔기`", "만든 물건을 팝니다.\n물건을 팔아서 돈을 얻습니다.")
                         .addField("`-나무 상점`", "상점 목록을 보여줍니다. 씨앗이나 기념품을 구매할 수 있습니다.");
                }
                else if( replaced.equals("관리") ){
                    embed.setDescription("서버 관리자 권한을 가진 사용자가 이용할 수 있는 명령어 목록입니다.")
                         .addField("`-단어차단 [차단할 단어]`", "차단 단어를 등록합니다.\n차단한 단어는 서버에서 볼 수 없게 됩니다.")
                         .addField("`-차단목록`", "차단한 단어 목록을 보여줍니다.")
                         .addField("`-차단삭제 [삭제할 단어]`", "차단 목록에서 단어를 삭제합니다.");
                }
                else {
                    embed.setTitle("에이마이너스 도움말")
                         .setDescription("프리픽스는 **\"-(하이픈)\"**입니다. 아래의 명령어들은 모두 **\"-\"**을 앞에 붙혀 사용해야 정상적으로 처리됩니다.")
                         .addField("`도움말`", "현재 보고 있는 도움말을 보여줍니다.")
                         .addField("`도움말 나무심기`", "나무심기 게임에 대한 도움말을 보여줍니다.")
                         .addField("`도움말 기능`", "봇의 기능에 대한 도움말을 보여줍니다.")
                         .addField("`도움말 관리`", "서버의 관리자 권한을 가진 사용자가 이용할 수 있는 명령어 목록을 보여줍니다.")
                         .addField("`굴러`", "데굴데굴 구릅니다.");
                }

                ch.sendMessage( userMention, embed );
            }
            else if( msg.endsWith("등록") ){
                embed.setTitle("등록하기");
                embed.setDescription("**`-등록 [닉네임]` 으로 등록을 진행해주세요.\n[닉네임]에 지정한 이름은 봇 내에서 이용됩니다.**```에이마이너스 봇에서 수집하는 개인정보는\n사용자 ID, 닉네임, 등록 시 입력하는 닉네임이며,\n봇의 기능 이외의 용도로 이용되지 않습니다.```");

                ch.sendMessage( userMention, embed );
            }
            else if( msg.contains("등록") && !msg.endsWith("등록") ) {
                try {
                    String nickname = msg.replace("-등록 ", "");

                    boolean isSucceed = manager.enrollment( nickname, userid );

                    if( isSucceed ) {
                        ch.sendMessage("```✅ " + nickname + "(으)로 등록이 완료되었습니다!```");
                        ch.sendMessage("등록이 완료되었습니다. 더 많은 에이마이너스의 컨텐츠를 즐겨보세요!");
                    } else {
                        ch.sendMessage("```이미 등록된 계정입니다. \"-프로필\" 로 확인해주세요.```");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ch.sendMessage("```오류가 발생했습니다. 등록 양식을 확인하고 다시 시도해주세요.```");
                }
            }
            else if( msg.contains("탈퇴확인") ){
                if( profile == null ){
                    ch.sendMessage("탈퇴할 프로필이 없습니다.");
                } else {
                    manager.secession(userid);
                    embed.setImage(new File("D:\\somthing I made\\AminusBot\\죽었어.png"))
                            .setTitle("탈퇴가 완료되었습니다.")
                            .setDescription("지금까지 에이마이너스 봇을 이용해주셔서 감사했습니다.");

                    ch.sendMessage(userMention, embed);
                }
            }
            else if( msg.contains("탈퇴") ){
                if( profile == null )
                    ch.sendMessage("탈퇴할 프로필이 없습니다.");
                else
                    ch.sendMessage("```정말로 탈퇴하시겠습니까? 확인을 원하시면 \"-탈퇴확인\" 을 입력해주세요.\n❗ 탈퇴 시 가진 아이템과 모든 재산이 사라집니다. ❗```");
            }
            else if( msg.contains("참") || msg.contains("cka") ){
                ch.sendMessage("어떻게 사람 이름이 참ㅋㅋㅋ");
            }
            else if ( msg.contains("골라") ){
                splitted = msg.replace("-골라 ", "").split(" ");
                int rand = (int)(Math.random()*splitted.length);

                ch.sendMessage( splitted[rand] + "이(가) 좋을 것 같은데.." );
            }
            else if( msg.endsWith("닉네임변경") ){
                ch.sendMessage("```❗ 올바른 사용법: -닉네임변경 [변경할 닉네임]```");
            }
            else if( msg.contains("닉네임변경") ){
                replaced = msg.replace("-닉네임변경 ", "");
                manager.stringDataChange(userid, "user_name", replaced);
                ch.sendMessage("```✅ 닉네임이 " + replaced + "(으)로 변경되었습니다.```");
            }
            else if( msg.contains("프로필") ){
                if( profile == null ){
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                } else {
                    embed.setTitle(profile.user_name + " 님의 프로필")
                            .setThumbnail( message.getAuthor().getAvatar() )
                            .addField("💵 가진 재산", profile.money + "원")
                            .addField("🔨 숙련도", profile.proficiency + "")
                            .addField("🏆 랭킹", "전체 " + profile.rank + "위");
                    ch.sendMessage( userMention, embed );
                }
            }
            else if( msg.contains("나무") ){
                if( manager.getProfile(userid) == null )
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                else {
                    try {
                        plantTree( msg, ch, embed, manager, userid,userMention );
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
            else if( msg.contains("돈내놔") || msg.contains("돈줘") || msg.contains("돈주세요") ){
                if( manager.getProfile(userid) == null )
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                else {
                    manager.dataEdit(userid, "money", 10000);
                    ch.sendMessage("돈줬어" + "\n`💵+10000`");
                }
            }
            else if( msg.contains("주작") ){
                if( manager.getProfile(userid) == null )
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                else {
                    if( auther.isBotOwner() ) {
                        manager.dataEdit(userid, "money", 10000000);
                        ch.sendMessage("날아오르라 주작이여" + "\n`💵+10000000`");
                    } else
                        ch.sendMessage("봇 주인의 더러운 수작...이 아닌 테스트용 커맨드입니다.");
                }
            }
            else if( msg.contains("돈없애줘") ){
                if( manager.getProfile(userid) == null )
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                else {
                    manager.dataChange(userid, "money", 0);
                    ch.sendMessage("응?" + "\n`💸가진 돈이 전부 사라졌습니다.`");
                }
            }
            else if( msg.contains("지갑") ){
                if( manager.getProfile(userid) == null )
                    ch.sendMessage("```❗ 사용자 등록을 해야 사용할 수 있는 명령어입니다. 먼저 \"-등록\"으로 등록을 진행해주세요.```");
                else {
                    ch.sendMessage("```" + profile.user_name + "님은 현재 " + profile.money + "💵 만큼의 재산을 가지고 있습니다.```");
                }
            }
            else if( msg.contains("랭킹") ){
                ArrayList<Profile> rank = new ArrayList<Profile>();
                rank = manager.getRank( userid );//top3의 Profile 객체를 0,1,2에, 메시지를 보낸 유저의 Profile 객체를 3에 담아서 넘김
                embed.setTitle("🏆재산 랭킹🏆")
                     .setDescription("🥇: "  + rank.get(0).user_name + "  " + rank.get(0).money + "원\n"
                        + "🥈: " + rank.get(1).user_name + "  " + rank.get(1).money + "원\n"
                        + "🥉: " + rank.get(2).user_name + "  " + rank.get(2).money + "원\n");
                ch.sendMessage( userMention, embed );
            }
            else if( msg.contains("단어차단") || msg.contains("차단목록") || msg.contains("차단삭제") ){
                List<Role> roles = auther.getRoles( server );
                boolean isManager = false;

                for( Role r : roles )
                    if( r.isManaged() )
                        isManager = true;

                if( isManager )
                    managerCommand( msg, ch );
                else
                    ch.sendMessage("```❗ 서버의 관리자만 사용할 수 있는 명령어입니다.```");
            }
            else if( msg.contains("건배") )
                ch.sendMessage( userMention, embed.setImage("https://blog.hubspot.com/hubfs/giphy_1-1.gif") );
            else if( msg.contains("에이") )
                ch.sendMessage("메모장 좀 그만 쓰면 좋겠는데..");
            else if( msg.contains("초대링크") ){
                ch.sendMessage("이 링크로 봇을 서버에 초대할 수 있습니다." + api.createBotInvite());
            }
            else
                ch.sendMessage("그런 명령어는 아무리 뒤져봐도 없는 것 같은데..");
        } );
    }

    private static void managerCommand( String msg, TextChannel ch ) {
        if( msg.contains("단어차단") ){
            ch.sendMessage("```제작중입니다.```");
        }
        else if( msg.contains("차단목록") ){
            ch.sendMessage("```제작중입니다.```");
        }
        else if( msg.contains("차단삭제") ){
            ch.sendMessage("```제작중입니다.```");
        }
    }

    public static void plantTree( String msg, TextChannel ch, EmbedBuilder embed, DBManager manager, long userid, String userMention ) throws ExecutionException, InterruptedException {
        String command = msg.replace("-나무 ", "");
        Profile profile = manager.getProfile( userid );
        String replaced;
        String[] splitted;

        if( command.equals("가방") ){
            embed.setTitle( profile.user_name + "님의 가방")
                    .setDescription( "돈 " + profile.money + "💵\n묘목 🌱X" + profile.seedlings + "\n목재 🧱X" + profile.woods )
                    .addField("팔 수 있는 아이템", "\n나무젓가락X" + profile.chopsticks +
                                                            "\n나무 접시X" + profile.plates +
                                                            "\n나무 열쇠고리X" + profile.keyrings +
                                                            "\n나무 조각품X" + profile.sculptures +
                                                            "\n나무 장난감X" + profile.toys +
                                                            "\n나무 의자X" + profile.chairs +
                                                            "\n나무 탁자X" + profile.tables +
                                                            "\n나무 그네X" + profile.swings, true)
                    .addField("기념품", "\n조각상X" + profile.figures +
                                                    "\n인형X" + profile.plushs +
                                                    "\n빛나는 유리구슬X" + profile.glassmarbles +
                                                    "\n빛나는 보석X" + profile.primogems, true);
            ch.sendMessage( userMention, embed );
        } else if( command.equals("현황") ){
            if( profile.planted_tree == 0 )
                ch.sendMessage("```🍃 너무나도 썰렁합니다. 나무가 단 한 그루도 심어져있지 않네요!```");
            else
                ch.sendMessage("```🌲 현재 " + profile.planted_tree + "그루의 나무가 심어져 있으며 " + (20-profile.planted_tree) + "만큼의 자리가 남아 있습니다.```");
        } else if( command.contains("심기") ){
            int plant = Integer.parseInt( replaced = command.replace("심기 ", "") );

            if( (profile.planted_tree + plant) <= 20 ){
                if( profile.seedlings >= plant && plant > 0 ) {
                    profile.planted_tree += plant;
                    manager.dataEdit(userid, "planted_tree", plant);
                    manager.dataEdit(userid, "seedlings", plant*-1);
                    ch.sendMessage("```✅ " + plant + "그루의 나무를 심었습니다. 현재 " + profile.planted_tree + "그루의 나무가 심어져 있습니다.\n    현재 " + (20-profile.planted_tree) + "만큼의 자리가 남아 있습니다.```");
                }
                else
                    ch.sendMessage("```❗ 최소 한 그루 이상은 심어야 합니다. 혹은 가진 묘목의 수가 부족합니다.```");
            }else
                ch.sendMessage("```❗ 자리가 부족합니다. 나무는 최대 20그루까지 심을 수 있습니다.```");
        } else if( command.contains("베기") ){
            int cut = Integer.parseInt( replaced = command.replace("베기 ", "") );
            int bonus = (int)(Math.random()*profile.proficiency);

            if( profile.planted_tree >= cut && cut > 0 ){
                profile.woods += cut + bonus;
                manager.dataEdit(userid, "woods", cut + bonus);
                manager.dataEdit(userid, "planted_tree", cut*-1);
                ch.sendMessage("```✅ " + cut + "그루의 나무를 베었습니다. 숙련도 보너스로 " + bonus + "개가 추가되어 현재 " + profile.woods + "개의 목재를 가지고 있습니다.```");
            }
            else
                ch.sendMessage("```❗ 벨 수 있는 나무의 수가 부족합니다. \"-나무 현황\" 으로 확인해주세요.```");
        } else if( command.equals("제작") ){
            embed.setTitle("제작 가능한 아이템")
                    .setDescription("🧱: 필요 목재\n🔨: 필요 숙련도")
                    .addField("1. 나무 젓가락", "🧱X2 🔨X0")
                    .addField("2. 나무 접시", "🧱X4 🔨X1")
                    .addField("3. 나무 열쇠고리", "🧱X6 🔨X2")
                    .addField("4. 나무 조각품", "🧱X8 🔨X4")
                    .addField("5. 나무 장난감", "🧱X10 🔨X6")
                    .addField("6. 나무 의자", "🧱X14 🔨X8")
                    .addField("7. 나무 책상", "🧱X16 🔨X10")
                    .addField("8. 나무 의자", "🧱X20 🔨X12")
                    .setFooter("-나무 제작 [번호] 로 아이템을 제작할 수 있습니다.\n현재 보유 목재: " + profile.woods + "🧱 숙련도: " + profile.proficiency + "🔨");
            ch.sendMessage( userMention, embed );
;       } else if( command.contains("제작") /*&& command.matches("(?<=\\$)[0-9]")*/ ){
            int num = Integer.parseInt( command.replace("제작 ", "") );
            boolean iscanmake = false;

            iscanmake = isCanMake( num, profile, manager );

            if( iscanmake ){
                manager.dataEdit(userid, "woods", manager.getItems(num).needWoods * -1);
                manager.dataEdit(userid, manager.getItems(num).item_name, 1);

                ch.sendMessage("```✅ " + manager.getItems(num).item_name + " 1개가 제작되었습니다.```");
            }else
                ch.sendMessage("```❗ 필요한 목재나 숙련도가 부족합니다.```");
        } else if( command.equals("팔기") ){
            embed.setTitle( profile.user_name + "님의 가방")
                    .addField("보유한 팔 수 있는 아이템", "\n1. 나무젓가락X" + profile.chopsticks +
                            "\n2. 나무 접시X" + profile.plates +
                            "\n3. 나무 열쇠고리X" + profile.keyrings +
                            "\n4. 나무 조각품X" + profile.sculptures +
                            "\n5. 나무 장난감X" + profile.toys +
                            "\n6. 나무 의자X" + profile.chairs +
                            "\n7. 나무 탁자X" + profile.tables +
                            "\n8. 나무 그네X" + profile.swings, true)
                    .setFooter("\"-나무 팔기 [번호] [개수]\" 로 입력해주세요.\nex) -나무 팔기 1 2 는 나무젓가락 두 개를 판매합니다.\n\"-나무 팔기 전부\" 로 입력하면 보유한 모든 아이템을 판매합니다.");
            ch.sendMessage( userMention, embed );
        }
        else if( command.contains("팔기") ){
            int totalmoney = 0, bonus = ((int)(Math.random()*201)*profile.proficiency);
            replaced = command.replace("팔기 ", "");

            if( replaced.equals("전부") ){
                if( profile.chopsticks > 0 )
                    totalmoney += (profile.chopsticks * 700) + bonus; manager.dataChange(userid, "chopsticks", 0);
                if( profile.plates > 0 )
                    totalmoney += (profile.plates * 1500) + bonus; manager.dataChange(userid, "plates", 0);
                if( profile.keyrings > 0 )
                    totalmoney += (profile.keyrings * 2100) + bonus; manager.dataChange(userid, "keyrings", 0);
                if( profile.sculptures > 0 )
                    totalmoney += (profile.sculptures * 3600) + bonus; manager.dataChange(userid, "sculptures", 0);
                if( profile.toys > 0 )
                    totalmoney += (profile.toys * 5000) + bonus; manager.dataChange(userid, "toys", 0);
                if( profile.chairs > 0 )
                    totalmoney += (profile.chairs * 6200) + bonus; manager.dataChange(userid, "chairs", 0);
                if( profile.tables > 0 )
                    totalmoney += (profile.tables * 7000) + bonus; manager.dataChange(userid, "tables", 0);
                if( profile.swings > 0 )
                    totalmoney += (profile.swings * 12000) + bonus; manager.dataChange(userid, "swings", 0);

                manager.dataEdit(userid, "money", totalmoney);

                ch.sendMessage("```✅ 모든 물건을 판매하여 " + totalmoney + "💵 만큼 벌어 현재 " + (profile.money+totalmoney) + "💵 만큼의 돈을 가지고 있습니다.```");
            }
            else {
                splitted = replaced.split(" ");//0 번호 1 개수
                Items items = manager.getItems( Integer.parseInt( splitted[0] ) );
                int num  = Integer.parseInt( splitted[1] );

                if( profile.getItem( items.item_name ) >= num ){
                    manager.dataEdit( userid, items.item_name, num*-1 );
                    manager.dataEdit( userid, "money", (num*items.minvalue)+((int)(Math.random()*201)*profile.proficiency) );

                    ch.sendMessage("```✅ " + items.item_name + " " + num + "개를 판매하여" + ((num*items.minvalue)+((int)(Math.random()*201)*profile.proficiency)) + "💵 만큼 벌어 현재 " + ((profile.money+(num*items.minvalue)+((int)(Math.random()*201)*profile.proficiency))) + "💵 만큼의 돈을 가지고 있습니다.```");
                }
                else
                    ch.sendMessage("```❗ 판매할 아이템의 수가 부족합니다.```");
            }
        }
        else if( command.equals("상점") ){
            boolean iscompleted = false;
            int needmoney = 0;
            Message message = ( new MessageBuilder()
                    .append("상점에 오신 것을 환영합니다!", MessageDecoration.BOLD)
                    //.addAttachment(new File("C:\\Users\\user\\Desktop\\자잘한거\\글임\\서버임티\\tkdwja.png")) 
                    .setEmbed( embed.setTitle("구매할 수 있는 아이템").setDescription("🌱 묘목 1000 💵\n🌲 묘목X5 4500 💵")
                    .addField( "기념품", "🏆 조각상 50000 💵\n🧸 인형 100000 💵\n🔮 빛나는 유리구슬 200000 💵\n💎 빛나는 보석 1000000 💵")
                    .setFooter( "현재 가지고 있는 돈: " + profile.money + " 💵\n10초 뒤에 자동으로 사라집니다." ))
                    .send( ch ) ).get();

            message.addReaction("🌱");
            message.addReaction("🌲");
            message.addReaction("🏆");
            message.addReaction("🧸");
            message.addReaction("🔮");
            message.addReaction("💎");

            Thread.sleep(10000);

            ch.sendMessage("시간이 초과되었습니다.");
            message.removeAllReactions();
            message.addReaction("❌");

            for ( Reaction re : message.getReactions() ){
                if( re.getCount() == 2){
                    if( re.getEmoji().equalsEmoji("🌱") )
                        needmoney += 1000;
                    else if( re.getEmoji().equalsEmoji("🌲") )
                        needmoney += 4500;
                    else if( re.getEmoji().equalsEmoji("🏆") )
                        needmoney += 50000;
                    else if( re.getEmoji().equalsEmoji("🧸") )
                        needmoney += 100000;
                    else if( re.getEmoji().equalsEmoji("🔮") )
                        needmoney += 200000;
                    else if( re.getEmoji().equalsEmoji("💎") )
                        needmoney += 1000000;

                    if( profile.money >= needmoney ) {
                        manager.dataEdit(userid, "money", needmoney*-1);
                        if( re.getEmoji().equalsEmoji("🌱") )
                            manager.dataEdit(userid, "seedlings", 1);
                        else if( re.getEmoji().equalsEmoji("🌲") )
                            manager.dataEdit(userid, "seedlings", 5);
                        else if( re.getEmoji().equalsEmoji("🏆") )
                            manager.dataEdit(userid, "figures", 1);
                        else if( re.getEmoji().equalsEmoji("🧸") )
                            manager.dataEdit(userid, "plushs", 1);
                        else if( re.getEmoji().equalsEmoji("🔮") )
                            manager.dataEdit(userid, "glassmarbles", 1);
                        else if( re.getEmoji().equalsEmoji("💎") )
                            manager.dataEdit(userid, "primogems", 1);

                        iscompleted = true;
                    }
                }
            }
            if( !iscompleted )
                ch.sendMessage("```❗ 구매한 항목이 없거나 돈이 부족합니다.```");
            else{
                ch.sendMessage("```✅ 아이템이 성공적으로 구매되었습니다. 사용한 돈: " + needmoney + "💵 \"-나무 가방\" 으로 확인해주세요.```");
            }
        }
        else if( command.equals("미니게임") ){
            embed.setTitle("미니게임")
                    .setDescription("돈을 벌 수 있는 미니게임 목록입니다. `-나무 미니게임 [번호]` 로 골라주세요.")
                    .addField("1. 같은 그림 맞추기", "화면에 나오는 그림을 따라 반응 이모지를 누르는 게임입니다.")
                    .addField("1. 사과받기", "떨어지는 사과를 알맞은 위치의 반응 이모지를 눌러 받아내는 게임입니다.");

            ch.sendMessage( userMention, embed );
        }
        else if( command.contains("미니게임")/*command.matches("(?<=\\$)[0-9]")*/ ){
            ch.sendMessage("```개발중입니다.```");
        }
        else {
            embed.setDescription("❔ 나무심기에 관한 커맨드는 \"-도움말 나무심기\" 를 참고해줘.");
            ch.sendMessage( userMention, embed );
        }
    }
    public static boolean isCanMake( int itemId, Profile profile, DBManager manager ){
        Items item = manager.getItems(itemId);

        return item.needWoods <= profile.woods && item.minproficiency <= profile.proficiency;
    }
    public static void printLOG( String content ){
        try {
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy.MM.dd(E) ahh:mm:ss Zz");
            Date timeNow = new Date();
            String tmNow = formatNow.format(timeNow);
            String path = "D:\\somthing I made\\AminusBot\\LOG.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            final PrintWriter pw = new PrintWriter(bw, true);
            String log = "[LOG]" + "[" + tmNow + "] " + content;

            System.out.println(log);
            pw.write(log + "\n");
            pw.flush();
            pw.close();
        } catch ( Exception e ){ e.printStackTrace(); }
    }
}