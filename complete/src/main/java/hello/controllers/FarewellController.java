package hello.controllers;

import hello.exceptions.InvalidLanguageException;
import hello.models.Farewell;
import hello.services.FarewellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//yes this is basically identical to the greeting controller but if you can say hello you should also be able to say goodbye
@RestController
public class FarewellController {
    @Autowired
    FarewellService farewellService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/farewell")
    public Farewell farewell(@RequestParam(value="name", defaultValue="World") String name, @RequestParam(value="language", defaultValue="en") String language) throws InvalidLanguageException {
        if (language.length() != 2) {
            //this is an extremely boring log message, it's just here to demonstrate that logging matters
            LOGGER.info("invalid language param provided [{}]", language);
            throw new InvalidLanguageException(language + " is not a valid language");
        }

        //never trust user input!
        //validating names is much more complex than what I'm doing - http://www.kalzumeus.com/2010/06/17/falsehoods-programmers-believe-about-names/
        String safeName = name.replaceAll("[^a-zA-Z -]", "");
        String safeLang = language.replaceAll("[^a-zA-Z -]", "");

        return farewellService.getFarewell(safeName, safeLang);
    }
}
