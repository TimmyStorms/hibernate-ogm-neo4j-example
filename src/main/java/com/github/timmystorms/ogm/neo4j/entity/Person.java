package com.github.timmystorms.ogm.neo4j.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field(analyze = Analyze.NO)
    private String firstName;

    @Field(analyze = Analyze.NO)
    private String lastName;
    
    @ManyToMany
    private Set<Item> items;

    public Person() {

    }

    public Person(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(final Set<Item> items) {
        this.items = items;
    }
    
    public boolean addItem(final Item item) {
        if (this.items == null) {
            this.items = new HashSet<>();
        }
        return this.items.add(item);
    }
    
    public boolean removeItem(final Item item) {
        return this.items.remove(item);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
