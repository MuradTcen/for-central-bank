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
class DocumentComparatorByTypeTest {

    private static List<Document> getDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(1, "document name", DocumentType.TYPE_1, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_0, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_3, DocumentSize.A0));
        documents.add(new Document(2, "document name", DocumentType.TYPE_3, DocumentSize.A0));
        documents.add(new Document(2, "document name", DocumentType.TYPE_0, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_2, DocumentSize.A0));

        return documents;
    }

    private static List<Document> getSortedDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(1, "document name", DocumentType.TYPE_0, DocumentSize.A0));
        documents.add(new Document(2, "document name", DocumentType.TYPE_0, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_1, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_2, DocumentSize.A0));
        documents.add(new Document(1, "document name", DocumentType.TYPE_3, DocumentSize.A0));
        documents.add(new Document(2, "document name", DocumentType.TYPE_3, DocumentSize.A0));

        return documents;
    }

    @Test
    void sort_whenListDocumentsSorted_thenEquals() {
        List<Document> actual = getDocuments();
        Collections.sort(actual, new DocumentComparatorByType());

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
        Collections.sort(actual, new DocumentComparatorByType());
        Collections.reverse(actual);

        List<Document> expected = getSortedDocuments();
        Collections.reverse(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sort_whenListDocumentsSortedDesc_thenNotEquals() {
        List<Document> actual = getDocuments();
        Collections.sort(actual, new DocumentComparatorByType());
        Collections.reverse(actual);

        List<Document> expected = getSortedDocuments();

        assertThat(actual).isNotEqualTo(expected);
    }

}