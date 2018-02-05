package hello.services;

import hello.models.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.actuate.metrics.CounterService;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

//this service doesn't do very much besides show that I like to have business logic separated from request validation
@Service
public class GreetingService {
    public static final String GREETING_LANGUAGE_METRIC_PREFIX = "greeting.language.";

    //anything returned by a REST endpoint should have an id!
    private final AtomicLong pretendId = new AtomicLong();

    @Autowired
    CounterService counterService;

    public static final String DEFAULT_LANGUAGE = "en";
    private HashMap<String, String> greetings;

    public GreetingService() {
        //setup some greetings!
        //ideally this would live in a database
        greetings = new HashMap<>();
        greetings.put("en", "Hello, %s!");
        greetings.put("es", "Hola, %s!");
        greetings.put("fr", "Bonjour, %s!");
    }

    public Greeting getGreeting(String name, String language) {
        counterService.increment(GREETING_LANGUAGE_METRIC_PREFIX + language);

        String template = greetings.get(language);
        if (template == null) {
            template = greetings.get(DEFAULT_LANGUAGE);
        }

        return new Greeting(pretendId.incrementAndGet(), String.format(template, name));
    }

}
