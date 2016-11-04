package org.telegram.commands;

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
public class GetPointsCommand extends BotCommand {

    public static final String LOGTAG = "GETPOINTSCOMMAND";

    public GetPointsCommand() {
        super("get_points", "With this command you can see your achieved score");
    }
    

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        
        LocalDatabaseManager localDatabase =  LocalDatabaseManager.getInstance();
        
        int userScore;
        userScore = localDatabase.checkPoints(user.getUserName()); 
        
        StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("Your score is ");
            messageBuilder.append(userScore);

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