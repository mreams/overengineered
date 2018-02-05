package hello.controllers;

import hello.exceptions.InvalidLanguageException;
import hello.models.Greeting;
import hello.services.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @Autowired
    GreetingService greetingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name, @RequestParam(value="language", defaultValue="en") String language) throws InvalidLanguageException {
        //validating names is much more complex than what I'm doing - http://www.kalzumeus.com/2010/06/17/falsehoods-programmers-believe-about-names/
        String safeName = name.replaceAll("[^a-zA-Z -]", "");

        if (language.length() != 2) {
            //this is an extremely boring log message, it's just here to demonstrate that logging matters
            LOGGER.info("invalid language param provided [{}]", language);
            throw new InvalidLanguageException(language + " is not a valid language");
        }

        //never trust user input!
        String safeLang = language.replaceAll("[^a-zA-Z -]", "");

        return greetingService.getGreeting(safeName, safeLang);
    }
}
