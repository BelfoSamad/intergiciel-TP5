package dz.esisba.commandside;

import dz.esisba.coreapi.DTO.BookDTO;
import dz.esisba.coreapi.DTO.LibraryDTO;
import dz.esisba.coreapi.commands.AddBookCommand;
import dz.esisba.coreapi.commands.LibraryCreationCommand;
import dz.esisba.coreapi.commands.RemoveBookCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

/**
 * - will be receiving the HTTP Requests and dispatching the Commands to the Axon Engine.
 *
 */
@RestController
@RequestMapping("command")
public class CommandController {

    //Axon provides a CommandGateway interface to trigger commands
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/library")
    public Future<String> createLibrary(@RequestBody LibraryDTO libraryDTO) {
        //send the command
        return commandGateway.send(new LibraryCreationCommand(libraryDTO.getLibraryId(), libraryDTO.getName()));
    }

    @PostMapping("/library/{library}/book")
    public Future<String> addBook(@PathVariable String library, @RequestBody BookDTO book) {
        return commandGateway.send(new AddBookCommand(library, book.getIsbn(), book.getTitle()));
    }

    @DeleteMapping("/library/{library}/{isbn}")
    public Future<String> addBook(@PathVariable String library, @PathVariable String isbn) {
        return commandGateway.send(new RemoveBookCommand(library, isbn));
    }
}
