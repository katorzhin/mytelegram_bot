package com.home.mytelegram_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        Message message = update.getMessage();


        try {
            execute(getResponseMessage(message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage greetingMessage(Message message){
        SendMessage response = new SendMessage();
        response.setText("Hello, " + message.getFrom().getFirstName() + ".Welcome to the telegram bot :)\n" +
                "You have send me: " + message.getText());
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    @Override
    public String getBotUsername() {
        return "katorzhin_bot";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(WHAT_THE_DATE_REQUEST);
        row1.add(WHAT_THE_TIME_REQUEST);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Zhopka,I LOVE YOU:))");
        row2.add("Button 3");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        markup.setKeyboard(rows);
        return markup;
    }
    private SendMessage getCurrentTimeResponse(Message message){
        SendMessage response = new SendMessage();
        response.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage getResponseMessage(Message message){
        switch (message.getText()){
            case WHAT_THE_TIME_REQUEST:
                return getCurrentTimeResponse(message);
            default:
                return greetingMessage(message);
        }
    }
}
