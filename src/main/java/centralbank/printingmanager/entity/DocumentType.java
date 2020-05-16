package centralbank.printingmanager.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentType {

    // ordinal указывает порядок типов, для возможности отсортировать по этому полю
    TYPE_0("type 0", 0),
    TYPE_1("type 1", 1),
    TYPE_2("type 2", 2),
    TYPE_3("type 3", 3),
    TYPE_4("type 4", 4),
    TYPE_5("type 5", 5);

    private final String name;
    private final int ordinal;

}
