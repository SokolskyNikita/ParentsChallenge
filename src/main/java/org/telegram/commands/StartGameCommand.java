package org.telegram.commands;

import org.telegram.database.DatabaseManager;
import org.telegram.localdatabase.LocalDatabaseManager;
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
public class StartGameCommand extends BotCommand {

    public static final String LOGTAG = "REGISTERCOMMAND";

    public StartGameCommand() {
        super("startchallenge", "Start a game session");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        LocalDatabaseManager databseManager = LocalDatabaseManager.getInstance();
        StringBuilder messageBuilder = new StringBuilder();
        
        if (!databseManager.checkIfUserExists(user.getUserName())){
            messageBuilder.append("You are not registered! Please register first!");
        }
        else if (databseManager.checkPoints(user.getUserName()) <= 0){
            messageBuilder.append("You don't have enough money! Please top up your account!");
        }
        else if (strings.length == 0){
            int currentMoney = databseManager.checkPoints(user.getUserName());
            messageBuilder.append("You have " + currentMoney + " credits. Please run the command with the amount you want to use.");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString() + ".");

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}