/**
 * NOTE FOR STUDENTS:
 * This repository uses the JPA EntityManager directly instead of extending
 * Spring Data's JpaRepository or CrudRepository. This is NOT the typical
 * "best practice" in modern Spring Boot applications, because:
 *
 *  - We need to write boilerplate code (findById, save, delete, etc.)
 *    that JpaRepository would normally provide for free.
 *  - We don't get advanced features like query derivation, paging,
 *    sorting, projections, or auditing.
 *
 * We are doing it this way for teaching purposes only:
 *  - To understand what JPA is doing under the hood.
 *  - To see how persistence actually works before introducing Spring Data's abstractions.
 *
 * In a real-world application, you would normally extend JpaRepository
 * (or CrudRepository) for cleaner, less error-prone, and more feature-rich code.
 *
 * In time, we will refactor this repository to use JpaRepository instead
 */
package model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.entities.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    public Customer save(Customer customer) {
        if (customer.getCustomerId() == null) {
            entityManager.persist(customer);
            return customer;
        } else {
            return entityManager.merge(customer);
        }
    }

    public List<Customer> findAll() {
        String query = "SELECT c FROM Customer c";
        return entityManager.createQuery(query, Customer.class).getResultList();
    }

    public void delete(Customer customer) {
        if (entityManager.contains(customer)) {
            entityManager.remove(customer);
        } else {
            entityManager.remove(entityManager.merge(customer));
        }
    }


}
