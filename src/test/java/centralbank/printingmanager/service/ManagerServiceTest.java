package centralbank.printingmanager.service;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.entity.DocumentSize;
import centralbank.printingmanager.entity.DocumentSort;
import centralbank.printingmanager.entity.DocumentType;
import centralbank.printingmanager.service.impl.ManagerServiceImpl;
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
    private final long AVERAGE_DURATION = 4;
    private static final long THREAD_SLEEP = 50;

    private static List<Document> getDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(13, "A", DocumentType.TYPE_3, DocumentSize.A3));
        documents.add(new Document(1, "b", DocumentType.TYPE_0, DocumentSize.A5));
        documents.add(new Document(1, "Y", DocumentType.TYPE_2, DocumentSize.A3));
        documents.add(new Document(1, "c", DocumentType.TYPE_3, DocumentSize.A4));

        return documents;
    }

    private static Document getDocument() {
        return new Document(1, "A", DocumentType.TYPE_3, DocumentSize.A3);
    }

    //todo: Получается решение "в лоб", т.к нет гарантий, что тесты успешно пройдут, в данном случае мы отправляем
    // 4 документа с duration 1 и чтобы дождаться их "печати", мы делаем "паузу" в текущем потоке, рассчитывая на то, что
    // очередь выполнится. Можно сделать лучше
    private static void sleep() {
        try {
            Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init() {
        managerService = new ManagerServiceImpl();
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
        managerService.stopPrintingAndGetCanceledDocuments();
        String actual = managerService.sendDocument(getDocument());

        String expected = DOCUMENT_SENT_FAIL;
        sleep();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sendStart_whenSendStart_thenReceiveMessage() {
        String actual = managerService.sendStart();

        String expected = MANAGER_STARTED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cancelDocument_whenSendCancel_thenReceiveMessage() {
        String actual = managerService.cancelDocument("");

        String expected = DOCUMENT_CANCELED;

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void cancelDocument_whenSendCancel_thenReceiveCanceledDocument() {
        Document document = getDocument();
        managerService.cancelDocument(document.getName());
        managerService.sendDocument(document);

        List<Document> actual = managerService.stopPrintingAndGetCanceledDocuments();
        Document expected = getDocument();

        assertThat(actual).hasSize(1).contains(expected);
    }

    @Test
    void getCanceledDocuments_whenCancelledDocuments_thenReturnCanceledDocuments() {
        List<Document> expected = getDocuments();
        for (Document document : expected) {
            managerService.sendDocument(document);
        }

        // Завершили печать документов
        List<Document> actual = managerService.stopPrintingAndGetCanceledDocuments();
        sleep();

        //Здесь плохо т.к нет гарантий, что первый документ "напечается"
        expected.remove(0);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAverageDurationPrintedDocuments_whenDocuments_thenReturnAverageDuration() {
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