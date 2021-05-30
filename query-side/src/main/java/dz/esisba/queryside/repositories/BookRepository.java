package dz.esisba.queryside.repositories;

import dz.esisba.queryside.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
