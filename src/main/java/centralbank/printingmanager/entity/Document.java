package centralbank.printingmanager.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class Document {

    @NotNull
    private final long duration;
    @NotNull
    private final String name;
    @NotNull
    private final DocumentType type;
    @NotNull
    private final DocumentSize size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return duration == document.duration &&
                Objects.equals(name, document.name) &&
                type == document.type &&
                size == document.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, name, type, size);
    }
}
