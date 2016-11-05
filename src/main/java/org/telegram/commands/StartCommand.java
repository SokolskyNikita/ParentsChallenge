package org.telegram.commands;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.telegram.database.DatabaseManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
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
        
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList();
        List<InlineKeyboardButton> firstRow = new ArrayList();
        List<InlineKeyboardButton> secondRow = new ArrayList();
        List<InlineKeyboardButton> thirdRow = new ArrayList();
        
    // FIRST ROW
        InlineKeyboardButton gameButton = new InlineKeyboardButton();
        gameButton.setText("Math Challenge");
        gameButton.setCallbackData("1");
        firstRow.add(gameButton);
        
    // SECOND ROW
        InlineKeyboardButton registerButton = new InlineKeyboardButton();
        registerButton.setText("Register");
        registerButton.setCallbackData("1");
        secondRow.add(registerButton);
        
        InlineKeyboardButton checkCreditButton = new InlineKeyboardButton();
        checkCreditButton.setText("Check Credit");
        checkCreditButton.setCallbackData("1");
        secondRow.add(checkCreditButton);
        
        InlineKeyboardButton startChallengeButton = new InlineKeyboardButton();
        startChallengeButton.setText("Start Challenge");
        startChallengeButton.setCallbackData("1");
        secondRow.add(startChallengeButton);
        
    // THIRD ROW
        InlineKeyboardButton spendCreditButton = new InlineKeyboardButton();
        spendCreditButton.setText("Spend credit");
        spendCreditButton.setCallbackData("1");
        secondRow.add(spendCreditButton);
    
        InlineKeyboardButton viewHighestScoreButton = new InlineKeyboardButton();
        viewHighestScoreButton.setText("View highest score");
        viewHighestScoreButton.setCallbackData("1");
        secondRow.add(viewHighestScoreButton);
    
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        replyMarkup.setKeyboard(keyboard);
     
        
        StringBuilder messageBuilder = new StringBuilder();

        String userName = user.getFirstName() + " " + user.getLastName();

            messageBuilder.append("Hi ").append(userName).append("\n");
            messageBuilder.append("i think we know each other already!");
        
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        
        answer.setReplyMarkup(replyMarkup);

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}