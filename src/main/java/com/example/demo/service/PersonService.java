package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person createPerson(Person person);
    Person findPersonByFirstNameAndLastName(String firstName, String lastName);
    void deletePerson(Long id);
    List<Person> getAllPersons();
    Person findById(Long id);
}
