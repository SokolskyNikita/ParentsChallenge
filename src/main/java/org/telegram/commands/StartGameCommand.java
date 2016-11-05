package org.telegram.commands;

import java.util.ArrayList;
import java.util.List;
import org.telegram.database.DatabaseManager;
import org.telegram.localdatabase.LocalDatabaseManager;
import org.telegram.telegrambots.api.methods.send.SendGame;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.games.CallbackGame;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
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
public class StartGameCommand extends BotCommand {

    public static final String LOGTAG = "REGISTERCOMMAND";

    public StartGameCommand() {
        super("startchallenge", "Start a game session");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        LocalDatabaseManager databseManager = LocalDatabaseManager.getInstance();
        StringBuilder messageBuilder = new StringBuilder();
        SendGame sendGame = new SendGame();
        sendGame.setChatId(chat.getId().toString());
        sendGame.setGameShortName("mathbattle");
        SendMessage answer = new SendMessage();

        if (!databseManager.checkIfUserExists(user.getUserName())) {
            messageBuilder.append("You are not registered! Please register first!");
        } else if (databseManager.checkPoints(user.getUserName()) <= 0) {
            messageBuilder.append("You don't have enough money! Please top up your account!");
        } else if (strings.length == 0) {
            int currentMoney = databseManager.checkPoints(user.getUserName());
            messageBuilder.append("You have " + currentMoney + " credits. Please run the command with the amount you want to use.");
        } else if (strings.length > 0) {
            messageBuilder.append("Starting game...");
            databseManager.addPoints(user.getUserName(), - Integer.parseInt(strings[0]));
            InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList();
            List<InlineKeyboardButton> firstRow = new ArrayList();
            InlineKeyboardButton gameButton = new InlineKeyboardButton();
            gameButton.setText("Forward this challenge to your friend!");
            gameButton.setSwitchInlineQuery("MathChallenge");
            firstRow.add(gameButton);
            keyboard.add(firstRow);
            replyMarkup.setKeyboard(keyboard);
            answer.setReplyMarkup(replyMarkup);
        }

        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString() + ".");

        try {
            absSender.sendMessage(answer);
            if (strings.length > 0) {
                absSender.sendGame(sendGame);
            }
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
