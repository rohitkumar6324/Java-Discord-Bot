package com.rohit.javabot.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageEvents extends ListenerAdapter{

    @Autowired
    private JDABuilder jdaBuilder;

    public void begin() throws InterruptedException {
        JDA jda = jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        jda.addEventListener(new MessageEvents());

    }


    public void onMessageReceived(MessageReceivedEvent event)
    {

        String messageReceived = event.getMessage().getContentRaw();
        String[] messageArr = messageReceived.split(" ");
        if(!event.getAuthor().isBot() && messageArr.length > 1){
            if(messageArr[0].equalsIgnoreCase("hello") || messageArr[0].equalsIgnoreCase("hi") && messageArr[1].equalsIgnoreCase("<@1038744752796991548>"))
                sayHello(event);
            else  if(messageArr[0].equalsIgnoreCase("sex") && messageArr[1].equalsIgnoreCase("<@1038744752796991548>"))
                giveSex(event);
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

}
