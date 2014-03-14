package com.github.timmystorms.ogm.neo4j;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import com.github.timmystorms.ogm.neo4j.entity.Item;
import com.github.timmystorms.ogm.neo4j.entity.Person;

public class OgmTest {

    public static void main(final String[] args) throws Exception {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory( "ogm-neo4j" );
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Person person = new Person("John", "Doe");
        em.persist(person);
        final Item iphone = new Item("iPhone");
        em.persist(iphone);
        final Item ipod = new Item("iPod");
        em.persist(ipod);
        person.addItem(iphone);
        person.addItem(ipod);
        Set<Person> persons = new HashSet<>();
        persons.add( person );
        iphone.setPersons( persons );
        ipod.setPersons( persons );
        em.merge(person);
        tx.commit();
        em.clear();

        em.getTransaction().begin();
        final Query query = em.createQuery("from Person p");
        System.out.println("Returned persons:" + query.getResultList().size());
        em.getTransaction().commit();
        em.close();
        emf.close();
        printDbContents();
    }
    
    private static void printDbContents() {
        final GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("C:/TEMP/ogm-neo4j");
        Transaction tx = graphDb.beginTx();
        try {
            for (final Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
                System.out.print(node.getId() + " ");
                for (final String key : node.getPropertyKeys()) {
                    System.out.print(key + " - " + node.getProperty(key) + ", ");
                }
                System.out.print("\n");
            }
            tx.success();
        } finally {
            tx.finish();
            graphDb.shutdown();
        }
    }
}
