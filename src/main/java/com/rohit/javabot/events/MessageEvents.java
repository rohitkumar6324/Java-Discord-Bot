package com.rohit.javabot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageEvents extends ListenerAdapter{

    @Autowired
    private JDABuilder jdaBuilder;

    private static List<String> pickupLinesArr = new ArrayList<>();

    static {
        try {
            File f1 = new File("src/main/java/com/rohit/javabot/utilities/pickuplines.txt");
            FileReader fr = new FileReader(f1);

            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                pickupLinesArr.add(line);
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void begin() throws InterruptedException {
        JDA jda = jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        jda.addEventListener(new MessageEvents());

    }


    public void onMessageReceived(MessageReceivedEvent event)
    {

        String messageReceived = event.getMessage().getContentRaw();
        String[] messageArr = messageReceived.trim().split(" ");
        String[] mentions = event
            .getMessage()
            .getMentions()
            .getUsers()
            .stream()
            .map(user -> user.getId())
            .toArray(String[]::new);

        if(!event.getAuthor().isBot() && messageArr.length > 1 && mentions[0].equalsIgnoreCase("1038744752796991548")){

            if(messageArr[0].equalsIgnoreCase("hello") || messageArr[0].equalsIgnoreCase("hi"))
                sayHello(mentions,event);

            else  if(messageArr[0].equalsIgnoreCase("sex"))
                giveSex(mentions,event);

            else if(messageArr[0].equalsIgnoreCase("flirt")){
                    flirt(mentions,event);
            }

            else if(messageArr[0].equalsIgnoreCase("bye")){
                sayBye(mentions,event);
            }

            else if(messageArr[0].equalsIgnoreCase("test")){
                sayByeTesting(mentions,event);
            }

        }

    }
    public void giveSex(String[] mentions,MessageReceivedEvent event){

        if(mentions.length > 1){
            event
                    .getChannel()
                    .sendMessage("Fuck me daddy, <@" + mentions[1] + "> 🥵")
                    .queue();
        }

        else{
            event
                    .getChannel()
                    .sendMessage("Fuck me daddy, " + "<@" + event.getAuthor().getId() + "> 🥵")
                    .queue();
        }
    }

    public void sayHello(String[] mentions,MessageReceivedEvent event){

        if(mentions.length > 1){
            event
                    .getChannel()
                    .sendMessage("Hi, <@" + mentions[1] + "> 👋")
                    .queue();
        }

        else{
            event
                    .getChannel()
                    .sendMessage("Hi, <@" + event.getAuthor().getId() + "> 👋")
                    .queue();
        }

    }

    public void flirt(String[] mentions,MessageReceivedEvent event){

        int lineNumber = (int)(Math.random()*(pickupLinesArr.size()));

        if(mentions.length > 1){
            event
                    .getChannel()
                    .sendMessage("<@" + mentions[1] + "> " + pickupLinesArr.get(lineNumber))
                    .queue();
        }

        else {

            event
                    .getChannel()
                    .sendMessage("<@" + event.getAuthor().getId() + "> " + pickupLinesArr.get(lineNumber))
                    .queue();

        }

    }

    public void sayBye(String[] mentions,MessageReceivedEvent event){

        if(mentions.length > 1){
            event
                    .getChannel()
                    .sendMessage("Bye, <@" + mentions[1] + "> 👋")
                    .queue();
        }

        else{
            event
                    .getChannel()
                    .sendMessage("Bye, <@" + event.getAuthor().getId() + "> 👋")
                    .queue();
        }

    }

    public void sayByeTesting(String[] mentions,MessageReceivedEvent event){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Chala Jaa",null);

        eb.setImage("https://raw.githubusercontent.com/rohitkumar6324/Java-Discord-Bot/master/src/main/java/com/rohit/javabot/utilities/chala-ja.gif?token=GHSAT0AAAAAABYDKLAKFPX3NOKOQ23MIARGY3IDYGQ");
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
//        channel.sendFiles(eb.build()).queue();
    }

}
