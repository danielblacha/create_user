package com.example.demo.controller;

import com.example.demo.exception.ResourceAlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String homePage(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "home";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public String addPagePerson(@ModelAttribute Person person, Model model) {
        if (personService.findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName()) != null) {
            throw new ResourceAlreadyExistException("User exist", "name ", person.getFirstName() + " " + person.getLastName());
        }
        personService.createPerson(person);
        model.addAttribute("persons", personService.getAllPersons());
        return "result";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(Model model, Person person) {
        model.addAttribute("person", person);
        return "error";
    }

    @RequestMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable(value = "id") Long id, Model model) {
        Person person = personService.findById(id);
        if (person == null) {
            throw new ResourceNotFoundException("User doesn't exist", "name ", person.getFirstName() + " " + person.getLastName());
        }
        personService.deletePerson(person.getId());
        model.addAttribute("persons", personService.getAllPersons());
        return "result";
    }
}
