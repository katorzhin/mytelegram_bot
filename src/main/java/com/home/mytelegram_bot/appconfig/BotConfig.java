package com.home.mytelegram_bot.appconfig;

import com.home.mytelegram_bot.MyTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "Katorzhin_bot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    @Bean
    public MyTelegramBot MySuperTelegramBot() {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
        options.setProxyType(proxyType);
        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);

        MyTelegramBot myTelegramBot = new MyTelegramBot(options);
        myTelegramBot.setWebHookPath(webHookPath);
        myTelegramBot.setBotUserName(botUserName);
        myTelegramBot.setBotToken(botToken);

        return myTelegramBot;
    }
}
