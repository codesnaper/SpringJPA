package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Person;

public interface IPersonRep {

    Person findById(int id) throws NotFoundException;
}
