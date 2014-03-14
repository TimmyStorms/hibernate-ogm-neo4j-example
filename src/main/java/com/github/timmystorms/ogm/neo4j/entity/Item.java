package com.github.timmystorms.ogm.neo4j.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Field(analyze = Analyze.NO)
    private String name;
    
    @ManyToMany(mappedBy = "items")
    private Set<Person> persons;
    
    public Item() {
        
    }
    
    public Item(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(final Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return name;
    }

}
