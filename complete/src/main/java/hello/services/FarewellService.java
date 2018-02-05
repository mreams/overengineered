package hello.services;

import hello.models.Farewell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FarewellService {
    public static final String FAREWELL_LANGUAGE_METRIC_PREFIX = "farewell.language.";

    //anything returned by a REST endpoint should have an id!
    private final AtomicLong pretendId = new AtomicLong();

    @Autowired
    CounterService counterService;

    public static final String DEFAULT_LANGUAGE = "en";
    private HashMap<String, String> farewells;

    public FarewellService() {
        //setup some farewells!
        //ideally this would live in a database
        farewells = new HashMap<>();
        farewells.put("en", "Goodbye, %s!");
        farewells.put("es", "Adios, %s!");
        farewells.put("fr", "Au revoir, %s!");
    }

    public Farewell getFarewell(String name, String language) {
        counterService.increment(FAREWELL_LANGUAGE_METRIC_PREFIX + language);

        String template = farewells.get(language);
        if (template == null) {
            template = farewells.get(DEFAULT_LANGUAGE);
        }

        return new Farewell(pretendId.incrementAndGet(), String.format(template, name));
    }
}
