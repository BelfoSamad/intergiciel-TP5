package dz.esisba.commandside;

import dz.esisba.coreapi.commands.AddBookCommand;
import dz.esisba.coreapi.commands.LibraryCreationCommand;
import dz.esisba.coreapi.commands.RemoveBookCommand;
import dz.esisba.coreapi.events.BookAddedEvent;
import dz.esisba.coreapi.events.BookRemovedEvent;
import dz.esisba.coreapi.events.LibraryCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * - is a pattern in DDD, the goal of the Aggregate is to make the unit consistent
 * - External reference are restricted to one member of the aggregate, designated as the
 * Aggregate Root.
 * - The Aggregate Root is the entity within the aggregate that is responsible for
 * maintaining this consistent state.
 * - Aggregate ≠ JPA Entity (not mapped in the database but used for consistency rules)
 */
@Aggregate//informs Axon’s auto configure for Spring that this class is an Aggregate instance.
@Data @AllArgsConstructor @NoArgsConstructor
public class LibraryAggregate {


    //We have a set of rules for each attribute to check when setting the commands
    @AggregateIdentifier
    private String libraryId;
    private String name;
    private List<String> isbnBooks;

    @CommandHandler
    //handle command of cleaning, put it in constructor because it's called by default
    public LibraryAggregate(LibraryCreationCommand cmd) {
        Assert.notNull(cmd.getLibraryId(), () -> "ID should not be null");
        Assert.notNull(cmd.getName(), () -> "Name should not be null");
        //notify that a new Library was created by publishing the LibraryCreatedEvent in the EventStore
        AggregateLifecycle.apply(new LibraryCreatedEvent(cmd.getLibraryId(), cmd.getName()));
    }

    //define the annotated method as a handler for published Events
    @EventSourcingHandler
    private void handle(LibraryCreatedEvent event) {
        libraryId = event.getLibraryId();
        name = event.getName();
        isbnBooks = new ArrayList<>();
    }

    @CommandHandler
    public void on(AddBookCommand cmd) throws Exception {
        Assert.notNull(cmd.getLibraryId(), () -> "ID should not be null");
        Assert.notNull(cmd.getIsbn(), () -> "Book ISBN should not be null");
        if (this.isbnBooks.contains(cmd.getIsbn())) throw new Exception("Book ISBN must be unique");
        AggregateLifecycle.apply(new BookAddedEvent(cmd.getLibraryId(), cmd.getIsbn(), cmd.getTitle()));
    }

    @EventSourcingHandler
    private void handle(BookAddedEvent event) {
        isbnBooks.add(event.getIsbn());
    }

    @CommandHandler
    public void on(RemoveBookCommand cmd) throws Exception {
        Assert.notNull(cmd.getLibraryId(), () -> "ID should not be null");
        Assert.notNull(cmd.getIsbn(), () -> "Book ISBN should not be null");
        if (!this.isbnBooks.contains(cmd.getIsbn())) throw new Exception("Book ISBN must be exist");
        AggregateLifecycle.apply(new BookRemovedEvent(cmd.getLibraryId(), cmd.getIsbn()));
    }

    @EventSourcingHandler
    private void handle(BookRemovedEvent event) {
        isbnBooks.remove(event.getIsbn());
    }
}
