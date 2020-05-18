package com.mate.velkey.epam.person.controller;

import com.mate.velkey.epam.person.model.Person;
import com.mate.velkey.epam.person.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getPersonList() {
        return personRepository.getPersonList();
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return personRepository.getPersonById(id);
    }

    @PostMapping
    public void createPerson(@RequestBody Person person) {
        personRepository.createPerson(person);
    }

    @PutMapping
    public void updatePerson(@RequestBody Person person) {
        personRepository.updatePerson(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@PathVariable("id") int id) {
        personRepository.deletePersonById(id);
    }
}
