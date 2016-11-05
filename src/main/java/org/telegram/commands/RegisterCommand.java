package org.telegram.commands;

import org.telegram.database.DatabaseManager;
import org.telegram.localdatabase.LocalDatabaseManager;
import org.telegram.telegrambots.api.methods.send.SendGame;
import org.telegram.telegrambots.api.methods.send.SendMessage;
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
public class RegisterCommand extends BotCommand {

    public static final String LOGTAG = "REGISTERCOMMAND";

    public RegisterCommand() {
        super("register", "With this command you can register an account");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        LocalDatabaseManager databseManager = LocalDatabaseManager.getInstance();
        StringBuilder messageBuilder = new StringBuilder();
       

        String userName = user.getFirstName() + " " + user.getLastName();

        if (databseManager.checkIfUserExists(user.getUserName())) {
            messageBuilder.append("Hi ").append(userName).append("\n");
            messageBuilder.append("i think we know each other already!");
        } else {
            databseManager.addUser(user.getUserName());
            messageBuilder.append("Welcome ").append(userName).append("\n");
            messageBuilder.append("you may now challenge your friends for a game!");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        

        try {
            absSender.sendMessage(answer);              
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}