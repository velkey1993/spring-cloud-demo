package com.mate.velkey.epam.person.repository;

import com.mate.velkey.epam.person.model.Person;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private final List<Person> personList = new ArrayList<>();

    public List<Person> getPersonList() {
        return personList;
    }

    @Nullable
    public Person getPersonById(int id) {
        return personList.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void createPerson(Person person) {
        personList.add(person);
    }

    public void updatePerson(Person person) {
        Person personById = getPersonById(person.getId());
        int index = personList.indexOf(personById);
        personList.set(index, person);
    }

    public void deletePersonById(int id) {
        Person personById = getPersonById(id);
        personList.remove(personById);
    }
}
