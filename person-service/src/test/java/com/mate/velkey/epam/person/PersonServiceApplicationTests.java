package com.mate.velkey.epam.person;

import com.mate.velkey.epam.person.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonServiceApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void testCreate() {
        Person person = new Person(1, "Joe", "Smith", 22);
        HttpEntity<Person> httpEntity = new HttpEntity<>(person);
        ResponseEntity<Person> responseEntity = testRestTemplate.exchange("/person", HttpMethod.POST, httpEntity, Person.class);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @Order(2)
    void testFindById() {
        Person person = testRestTemplate.getForObject("/person/{id}", Person.class, 1);
        Assertions.assertNotNull(person);
        Assertions.assertEquals(1, person.getId());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Person person = new Person(1, "Joe", "Smith", 30);
        HttpEntity<Person> httpEntity = new HttpEntity<>(person);
        ResponseEntity<Person> responseEntity = testRestTemplate.exchange("/person", HttpMethod.PUT, httpEntity, Person.class);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @Order(4)
    void testDelete() {
        testRestTemplate.delete("/person/{id}", 1);
    }
}
