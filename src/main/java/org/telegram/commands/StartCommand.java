package org.telegram.commands;

import java.io.File;
import org.telegram.database.DatabaseManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * This commands starts the conversation with the bot
 *
 * @author Timo Schulz (Mit0x2)
 */
public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        
        StringBuilder messageBuilder = new StringBuilder();

        String userName = user.getFirstName() + " " + user.getLastName();

            messageBuilder.append("Hi ").append(userName).append("\n");
            messageBuilder.append("Please register!");
        
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        
        SendPhoto logo = new SendPhoto();
        File f = new File("C:/tmp/logo.png");
        logo.setNewPhoto(f);
        logo.setCaption("Welcome to Parents Challenge!");
        logo.setChatId(chat.getId().toString());
                

        try {
            absSender.sendMessage(answer);
            absSender.sendPhoto(logo);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
} 