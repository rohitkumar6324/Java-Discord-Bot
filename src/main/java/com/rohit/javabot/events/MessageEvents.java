package com.rohit.javabot.events;

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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
        String[] messageArr = messageReceived.split(" ");
        if(!event.getAuthor().isBot() && messageArr.length > 1 && messageArr[1].equalsIgnoreCase("<@1038744752796991548>")){
            if(messageArr[0].equalsIgnoreCase("hello") || messageArr[0].equalsIgnoreCase("hi"))
                sayHello(event);
            else  if(messageArr[0].equalsIgnoreCase("sex"))
                giveSex(event);
            else if(messageArr[0].equalsIgnoreCase("flirt")){
                flirt(event);
            }
        }

    }
    public void giveSex(MessageReceivedEvent event){
        event
                .getChannel()
                .sendMessage("Fuck me daddy, " + "<@" + event.getAuthor().getId() + "> 🥵")
                .queue();
    }

    public void sayHello(MessageReceivedEvent event){
        event
                .getChannel()
                .sendMessage("Hi, <@" + event.getAuthor().getId() + "> 👋")
                .queue();
    }

    public void flirt(MessageReceivedEvent event){

        try {
            File f1 = new File("src/main/java/com/rohit/javabot/utilities/pickuplines.txt");
            FileReader fr = new FileReader(f1);

            BufferedReader br = new BufferedReader(fr);
            String line;
//            List<String> pickupLinesArr = new ArrayList<>();
            while((line = br.readLine()) != null){
                pickupLinesArr.add(line);
            }

            int lineNumber = (int)(Math.random()*(pickupLinesArr.size()));
            event
                    .getChannel()
                    .sendMessage("<@" + event.getAuthor().getId() + "> " + pickupLinesArr.get(lineNumber))
                    .queue();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
