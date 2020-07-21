package com.ea.chargeahead.controller;

import com.ea.chargeahead.model.Person;
import com.ea.chargeahead.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

@RestController
public class PersonController {

    @Autowired
    PersonService ps;

    @GetMapping("/all")
    public Hashtable<String, Person> getAll(){
        return ps.getAll();
    }
//
//    @RequestMapping("{id}")
//    public Person getPerson(@PathVariable("id") String id){
//        return ps.getPerson(id);
//    }
}
