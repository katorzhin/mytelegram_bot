package com.home.mytelegram_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class MyTelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        SendMessage response = new SendMessage();
        response.setText("Hello, " + message.getFrom().getFirstName() + ".Welcome to the telegram bot :)\n" +
                "You have send me: " + message.getText());
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "katorzhin_bot";
    }

    @Override
    public String getBotToken() {
        return "<botToken>";
    }

    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Button 1");
        row1.add("Button 2");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Button 3");
        row2.add("Button 3");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        markup.setKeyboard(rows);
        return markup;
    }
}
