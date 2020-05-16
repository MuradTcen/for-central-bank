package centralbank.printingmanager.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentSort {

    DURATION("DURATION"),
    NAME("NAME"),
    SIZE("SIZE"),
    TYPE("TYPE"),
    DEFAULT("DEFAULT");

    private final String name;
}
