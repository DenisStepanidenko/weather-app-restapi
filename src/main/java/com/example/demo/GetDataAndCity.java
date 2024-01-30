package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class GetDataAndCity {

    @FXML
    private TextField getCity;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text pressure;

    @FXML
    private Text tempFeel;

    @FXML
    private Text windSpeed;

    @FXML
    private Text humidity;

    @FXML
    private Text errorMessage;

    @FXML
    void initialize() {
        getData.setOnAction(actionEvent -> {
            errorMessage.setText("");
            String userCity = getCity.getText().trim();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + userCity + "&appid=83b2d12bfc5031df682a83484923586a&units=metric";

            String answer = getUrlContent(url);

            if(answer == null){
                errorMessage.setText("Неверно введён город/сервер недоступен");
                // теперь нужно сбросить все показатели с картинки
                resetConfiguration();
            }
            else{
                JSONObject jsonObject = new JSONObject(answer);
                temp.setText("Температура:" + jsonObject.getJSONObject("main").getDouble("temp"));
                pressure.setText("Давление:" + jsonObject.getJSONObject("main").getDouble("pressure"));
                tempFeel.setText("Ощущается:" + + jsonObject.getJSONObject("main").getDouble("feels_like"));
                windSpeed.setText("Скорость ветра:" + + jsonObject.getJSONObject("wind").getDouble("speed"));
                humidity.setText("Влажность:" + jsonObject.getJSONObject("main").getDouble("humidity"));
            }

        });
    }


    /**
     * Метод, который получается JSON ответ в виде строки
     * @param urlAddress - адрес обращения к REST API (сайт - https://openweathermap.org/api)
     * @return возвращаем JSON ответ в виде строки
     */
    public String getUrlContent(String urlAddress) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            return null;
        }

        return content.toString();
    }


    /**
     * Метод, который сбрасывает все показатели, когда неверно введён город
     */
    private void resetConfiguration(){
        temp.setText("Температура:");
        pressure.setText("Давление:");
        tempFeel.setText("Ощущается:");
        windSpeed.setText("Скорость ветра:");
        humidity.setText("Влажность:");
    }


}