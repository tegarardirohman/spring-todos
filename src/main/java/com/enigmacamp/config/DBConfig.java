package com.enigmacamp.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DBConfig {
    private static  EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction transaction;

    public static EntityManager em() {
        if (em == null) {
            try {
                emf = Persistence.createEntityManagerFactory("enigma-persistence");
                em = emf.createEntityManager();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return em;
    }
}
