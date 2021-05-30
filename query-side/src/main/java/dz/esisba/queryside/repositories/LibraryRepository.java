package dz.esisba.queryside.repositories;

import dz.esisba.queryside.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String> {
}
