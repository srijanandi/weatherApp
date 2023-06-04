package com.example.weatherApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1")
public class weatherController {

	    private final WebClient webClient;

	    @Value("${api.key}")
	    private String apiKey;

	    public weatherController() {
	        this.webClient = WebClient.create();
	    }
//API link: http://localhost:8080/api/v1/weather?cities=Pune,London
    @GetMapping("/weather")
    public String getWeatherForecast(@RequestParam("cities") String[] cities) {
        StringBuilder result = new StringBuilder();
        System.out.println(apiKey);
        for (String city : cities) {
            String weatherUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                    city +
                    "&appid=" +
                    apiKey;

            String response = webClient.get()
                    .uri(weatherUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            result.append(city).append(":\n").append(response).append("\n\n");
        }
        return result.toString();
    }
    }
