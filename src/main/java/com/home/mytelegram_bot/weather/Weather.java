package com.home.mytelegram_bot.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class Weather {
    public static String getWeather(String city, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city
                + "Kharkiv&appid=27be72fc78cb2c2e3a5443d6f8aff09c");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result="";
        while (in.hasNext()){
            result+=in.nextLine();

        }
        return result;
    }
}
