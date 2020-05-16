package centralbank.printingmanager.entity.comparator;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.entity.DocumentSize;
import centralbank.printingmanager.entity.DocumentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DocumentComparatorByNameTest {

    private static List<Document> getDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(600, "A", DocumentType.TYPE_1, DocumentSize.A0));
        documents.add(new Document(1, "a", DocumentType.TYPE_0, DocumentSize.A1));
        documents.add(new Document(7, "B", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(2, "A", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(7, "a", DocumentType.TYPE_0, DocumentSize.A5));
        documents.add(new Document(-10, "c", DocumentType.TYPE_2, DocumentSize.A3));

        return documents;
    }

    private static List<Document> getSortedDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(600, "A", DocumentType.TYPE_1, DocumentSize.A0));
        documents.add(new Document(2, "A", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(7, "B", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(1, "a", DocumentType.TYPE_0, DocumentSize.A1));
        documents.add(new Document(7, "a", DocumentType.TYPE_0, DocumentSize.A5));
        documents.add(new Document(-10, "c", DocumentType.TYPE_2, DocumentSize.A3));

        return documents;
    }

    @Test
    void sort_whenListDocumentsSorted_thenEquals() {
        List<Document> actual = getDocuments();
        Collections.sort(actual, new DocumentComparatorByName());

        List<Document> expected = getSortedDocuments();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sort_whenListDocumentsUnsorted_thenNotEquals() {
        List<Document> actual = getDocuments();

        List<Document> expected = getSortedDocuments();

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void sort_whenListDocumentsSortedDesc_thenEquals() {
        List<Document> actual = getDocuments();
        Collections.sort(actual, new DocumentComparatorByName());
        Collections.reverse(actual);

        List<Document> expected = getSortedDocuments();
        Collections.reverse(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sort_whenListDocumentsSortedDesc_thenNotEquals() {
        List<Document> actual = getDocuments();
        Collections.sort(actual, new DocumentComparatorByName());
        Collections.reverse(actual);

        List<Document> expected = getSortedDocuments();

        assertThat(actual).isNotEqualTo(expected);
    }

}