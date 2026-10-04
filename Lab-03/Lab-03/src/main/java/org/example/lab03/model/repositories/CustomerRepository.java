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
package org.example.lab03.model.repositories;

import org.example.lab03.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
