package com.ea.chargeahead.service;

import com.ea.chargeahead.model.Person;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class PersonService {

    Hashtable<String, Person> persons = new Hashtable<String, Person>();

    public PersonService() {
        Person p = new Person();
        p.setAge(23);
        p.setFirstName("Kallol");
        p.setLastName("Bairagi");
        p.setId("1");
        persons.put("1", p);

        p = new Person();
        p.setAge(24);
        p.setFirstName("Udit");
        p.setLastName("Gupta");
        p.setId("2");
        persons.put("2", p);
    }

    public Person getPerson(String id) {
        if (persons.containsKey(id))
            return persons.get(id);
        else
            return null;
    }

    public Hashtable<String, Person> getAll() {
        return persons;
    }

}
