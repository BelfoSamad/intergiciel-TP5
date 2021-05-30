package dz.esisba.queryside.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Library {
    @Id
    private String libraryId;
    private String name;
    @OneToMany(mappedBy = "library")
    private List<Book> Books;
}
