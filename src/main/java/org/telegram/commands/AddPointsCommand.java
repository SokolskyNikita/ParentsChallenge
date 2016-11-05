package org.telegram.commands;

import java.util.ArrayList;
import java.util.List;
import org.telegram.localdatabase.LocalDatabaseManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * This commands starts the conversation with the bot
 *
 * @author Timo Schulz (Mit0x2)
 */
public class AddPointsCommand extends BotCommand {

    public static final String LOGTAG = "GETPOINTSCOMMAND";

    public AddPointsCommand() {
        super("addcredits", "With this command you can add points");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        LocalDatabaseManager localDatabase = LocalDatabaseManager.getInstance();

        int userScore;
        userScore = localDatabase.checkPoints(user.getUserName());

        StringBuilder messageBuilder = new StringBuilder();
        int addedMoney = 0;

        if (strings.length > 0) {
            addedMoney = Integer.parseInt(strings[0]);
            localDatabase.addPoints(user.getUserName(), addedMoney);
            messageBuilder.append("Your score is now ");
            messageBuilder.append(userScore + addedMoney);
        }
        else {
            messageBuilder.append("Please specify how much credits you want to add!");
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
