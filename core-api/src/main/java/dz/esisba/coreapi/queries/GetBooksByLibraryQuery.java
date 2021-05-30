package dz.esisba.coreapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class GetBooksByLibraryQuery {
    private String LibraryId;
}
