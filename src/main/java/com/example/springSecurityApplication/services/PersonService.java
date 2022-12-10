package com.example.springSecurityApplication.services;

import com.example.springSecurityApplication.models.Person;
import com.example.springSecurityApplication.models.Product;
import com.example.springSecurityApplication.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Данный метод позволяет вернуть всех пользователей


    public Person findByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
    public List<Person>getAllPerson(){
        return personRepository.findAll();
    }

    //Данный метод позволяет вернуть пользователя по id
    public Person getPersonId(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    //Данный метод позволяет сохранить пользователя
    //@Transactional
    //public void savePerson(Person person) {
    //    personRepository.save(person);
    //}

    //Данный метод позволяет обновить данные пользователя
    //@Transactional
    //public void updatePerson(int id, Person person) {
    //    person.setId(id);
    //    personRepository.save(person);
    //}

    //Данный метод позволяет удалить пользователя по id
    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

}
