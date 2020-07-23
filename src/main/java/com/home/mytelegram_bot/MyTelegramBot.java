package com.home.mytelegram_bot;

import com.home.mytelegram_bot.weather.Model;
import com.home.mytelegram_bot.weather.Weather;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String WHAT_THE_TIME_REQUEST = "What the time?";
    private static final String WHAT_THE_DATE_REQUEST = "What the date?";

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage response = new SendMessage();
        Message message = update.getMessage();
        Model model = new Model();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                 sendMsg(message,"Hello "+message.getFrom().getFirstName()+". " +
                         "Welcome to the katorzhin telegram bot:)");
                    break;
                case "/help":
                    sendMsg(message, "Can i help you?");
                    break;
                case "/settings":
                    sendMsg(message, "What will we do?");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "katorzhin_bot";
    }

    @Override
    public String getBotToken() {
        return "1190272320:AAHrYGmpPSuevwCCLD-HW6R9_QmlNiT5YJw";
    }
    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(getMainMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(WHAT_THE_DATE_REQUEST);
        row1.add(WHAT_THE_TIME_REQUEST);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        markup.setKeyboard(rows);
        return markup;
    }

    private SendMessage greetingMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setText("Hello, " + message.getFrom().getFirstName() + ".Welcome to the telegram bot :)\n" +
                "You have send me: " + message.getText());
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage getCurrentTimeResponse(Message message) {
        SendMessage response = new SendMessage();
        response.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage getCurrentDateResponse(Message message) {
        SendMessage response = new SendMessage();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        response.setText(format.format(date));
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage getResponseMessage(Message message) {
        switch (message.getText()) {
            case WHAT_THE_TIME_REQUEST:
                return getCurrentTimeResponse(message);
            case WHAT_THE_DATE_REQUEST:
                return getCurrentDateResponse(message);
            default:
                return greetingMessage(message);
        }
    }
}
