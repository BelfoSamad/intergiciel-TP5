package dz.esisba.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddBookCommand {

    @TargetAggregateIdentifier
    private final String libraryId;
    private final String isbn;
    private final String title;
}
