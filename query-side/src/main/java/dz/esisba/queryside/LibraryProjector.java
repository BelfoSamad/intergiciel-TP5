package dz.esisba.queryside;

import dz.esisba.coreapi.events.BookAddedEvent;
import dz.esisba.coreapi.events.BookRemovedEvent;
import dz.esisba.coreapi.events.LibraryCreatedEvent;
import dz.esisba.coreapi.queries.GetBooksByLibraryQuery;
import dz.esisba.queryside.entities.Book;
import dz.esisba.queryside.entities.Library;
import dz.esisba.queryside.repositories.BookRepository;
import dz.esisba.queryside.repositories.LibraryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * - Commands should be visible in Query side, this synchronization is done here
 */
@Component
public class LibraryProjector {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void addLibrary(LibraryCreatedEvent event) throws Exception {
        Library libraryEntity = new Library(event.getLibraryId(), event.getName(), null);
        libraryRepository.save(libraryEntity);
    }

    @EventHandler
    public void addBook(BookAddedEvent event) {
        Library library = libraryRepository.findById(event.getLibraryId()).get();
        bookRepository.save(new Book(event.getIsbn(), event.getTitle(), library));
    }

    @EventHandler
    public void removeBook(BookRemovedEvent event) {
        bookRepository.deleteById(event.getIsbn());
    }

    //Some queries must be managed through special methods, used when calling query from query controller
    @QueryHandler
    public List<Book> getBooks(GetBooksByLibraryQuery query) {
        return libraryRepository.findById(query.getLibraryId()).get().getBooks();
    }

}
