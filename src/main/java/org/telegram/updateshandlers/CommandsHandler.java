package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.commands.HelloCommand;
import org.telegram.commands.HelpCommand;
import org.telegram.commands.RegisterCommand;
import org.telegram.commands.StartCommand;
import org.telegram.commands.StartGameCommand;
import org.telegram.commands.StopCommand;
import org.telegram.commands.GetPointsCommand;
import org.telegram.database.DatabaseManager;
import org.telegram.services.Emoji;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * This handler mainly works with commands to demonstrate the Commands feature of the API
 *
 * @author Timo Schulz (Mit0x2)
 */
public class CommandsHandler extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";

    /**
     * Constructor.
     */
    public CommandsHandler() {
        register(new HelloCommand());
        register(new StartCommand());
        register(new StopCommand());
        register(new RegisterCommand());
        register(new StartGameCommand());
        register(new GetPointsCommand());

        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId().toString());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Hack team comes to rescue! " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        
        if (update.hasCallbackQuery()) {
            System.out.println(update.getCallbackQuery().getData());

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setUrl("https://tbot.xyz/math/");
            answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
            answerCallbackQuery.setShowAlert(false);
            try {
                answerCallbackQuery(answerCallbackQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();  

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId().toString());
                echoMessage.setText("Hey heres your message:\n" + message.getText());

                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.COMMANDS_USER;
    }

    @Override
    public String getBotToken() {
        return BotConfig.COMMANDS_TOKEN;
    }
}
