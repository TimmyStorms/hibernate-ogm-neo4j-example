package com.github.timmystorms.ogm.neo4j;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.TransactionManager;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import com.github.timmystorms.ogm.neo4j.entity.Item;
import com.github.timmystorms.ogm.neo4j.entity.Person;

public class OgmWithJTATest {

    public static TransactionManager extractTransactionManager(EntityManagerFactory factory) {
        SessionFactoryImplementor sessionFactory = factory.unwrap(SessionFactoryImplementor.class);
        return sessionFactory.getServiceRegistry().getService(JtaPlatform.class).retrieveTransactionManager();
    }

    public static void main(final String[] args) throws Exception {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jta-neo4j");
        final TransactionManager transactionManager = extractTransactionManager(emf);
        transactionManager.begin();
        EntityManager em = emf.createEntityManager();
        Person person = new Person("John", "Doe");
        em.persist(person);
        final Item iphone = new Item("iPhone");
        em.persist(iphone);
        final Item ipod = new Item("iPod");
        em.persist(ipod);
        person.addItem(iphone);
        person.addItem(ipod);
        Set<Person> persons = new HashSet<>();
        persons.add(person);
        iphone.setPersons(persons);
        ipod.setPersons(persons);
        em.merge(person);
        transactionManager.commit();
        em.close();

        em = emf.createEntityManager();
        transactionManager.begin();
        Query query = em.createQuery("from Person p");
        System.out.println("Persons:" + query.getResultList().size());
        query = em.createQuery("from Item i");
        System.out.println("Items:" + query.getResultList().size());
        transactionManager.commit();
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
