package edu.jared.patterns.observer;

import java.util.Observable;

public class CurrentConditionsDisplay implements java.util.Observer, DisplayElement {
    private Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        this.observable.addObserver(this);
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity.");
    }

    public void update(Observable observable, Object o) {
        if (observable instanceof  WeatherData) {
            WeatherData weatherData = (WeatherData)observable;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }
}
