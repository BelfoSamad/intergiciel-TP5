package dz.esisba.queryside;

import dz.esisba.coreapi.queries.GetBooksByLibraryQuery;
import dz.esisba.queryside.entities.Book;
import dz.esisba.queryside.entities.Library;
import dz.esisba.queryside.repositories.LibraryRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("query")
public class QueryLibraryAPI {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/library/{id}")
    public Library getLibraryById(@PathVariable String id) {
        return libraryRepository.findById(id).get();
    }

    @GetMapping("/library")
    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    @GetMapping("/library/{id}/book")
    public List<Book> getBooksByLibrary(@PathVariable String id) {
        return libraryRepository.findById(id).get().getBooks();
    }

    /*
    //Used to access @QueryHandler method from query controller, helpfull in
    //  - will be matched using the type of the query
    //  - and the exact match for the response type of the query
    @GetMapping("/library/{id}/book")
    public Future<List<Book>> getBooksByLibrary(@PathVariable String id) {
        return queryGateway.query(new GetBooksByLibraryQuery(id),
                ResponseTypes.multipleInstancesOf(Book.class));
    }*/
}
