package dz.esisba.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class LibraryCreationCommand {
    @TargetAggregateIdentifier
    private final String libraryId;
    private final String name;
}
