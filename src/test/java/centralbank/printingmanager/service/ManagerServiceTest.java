package centralbank.printingmanager.service;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.entity.DocumentSize;
import centralbank.printingmanager.entity.DocumentSort;
import centralbank.printingmanager.entity.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ManagerServiceTest {

    private ManagerService managerService;
    private final String DOCUMENT_SENT_SUCCESS = "Документ отправлен на печать";
    private final String DOCUMENT_SENT_FAIL = "Принтер не запущен, необходимо запустить принтер, чтобы отправить документ на печать";
    private final String MANAGER_STARTED = "Принтер запущен";
    private final String DOCUMENT_CANCELED = "Документ не будет напечатан, если он находится в очереди печати";
    private final long AVERAGE_DURATION = 1;
    private static final long THREAD_SLEEP = 50;

    private static List<Document> getDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(1, "A", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(1, "b", DocumentType.TYPE_0, DocumentSize.A5));
        documents.add(new Document(1, "Y", DocumentType.TYPE_2, DocumentSize.A3));
        documents.add(new Document(1, "c", DocumentType.TYPE_3, DocumentSize.A4));

        return documents;
    }

    private static Document getDocument() {
        return new Document(1, "A", DocumentType.TYPE_3, DocumentSize.A3);
    }

    private static void sleep() {
        try {
            Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init() {
        managerService = new ManagerService();
    }


    @Test
    void sendDocument_whenDocumentsPrinted_thenEquals() {
        List<Document> expected = getDocuments();
        for (Document document : expected) {
            managerService.sendDocument(document);
        }

        List<Document> actual = managerService.getPrintedDocuments(DocumentSort.DEFAULT, "");
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sendDocument_whenDocumentsPrinted_thenNotEquals() {
        List<Document> expected = getDocuments();
        for (Document document : expected) {
            managerService.sendDocument(document);
        }

        expected.add(new Document(1, "c", DocumentType.TYPE_3, DocumentSize.A4));
        List<Document> actual = managerService.getPrintedDocuments(DocumentSort.DEFAULT, "");
        sleep();

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void sendDocument_whenDocumentPrinted_thenEquals() {
        String actual = managerService.sendDocument(getDocument());

        String expected = DOCUMENT_SENT_SUCCESS;
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sendDocument_whenDocumentPrinted_thenNotEquals() {
        // Завершили печать документов
        managerService.getCanceledDocuments();
        String actual = managerService.sendDocument(getDocument());

        String expected = DOCUMENT_SENT_FAIL;
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sendStart() {
        String actual = managerService.sendStart();

        String expected = MANAGER_STARTED;
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cancelDocument() {
        String actual = managerService.cancelDocument("");

        String expected = DOCUMENT_CANCELED;
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getCanceledDocuments() {
        List<Document> expected = getDocuments();
        for (Document document : expected) {
            managerService.sendDocument(document);
        }

        // Завершили печать документов
        List<Document> actual = managerService.getCanceledDocuments();

        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAverageDurationPrintedDocuments() {
        List<Document> documents = getDocuments();
        for (Document document : documents) {
            managerService.sendDocument(document);
        }

        managerService.getPrintedDocuments(DocumentSort.DEFAULT, "");
        sleep();
        long actual = managerService.getAverageDurationPrintedDocuments();
        long expected = AVERAGE_DURATION;

        assertThat(actual).isEqualTo(expected);
    }
}