package centralbank.printingmanager.service;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.entity.DocumentSort;
import centralbank.printingmanager.entity.comparator.DocumentComparatorByDuration;
import centralbank.printingmanager.entity.comparator.DocumentComparatorByName;
import centralbank.printingmanager.entity.comparator.DocumentComparatorBySize;
import centralbank.printingmanager.entity.comparator.DocumentComparatorByType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class ManagerService {

    // Храним принтеры в managers, в данном случае есть дефолтный принтер на который мы направляем документы
    // Можно расширить функционал и добавить другие принтеры, и в роуты добавить явное указание принтера
    // на который отправляются документы на печать
    private HashMap<String, PrinterService> managers = new HashMap<>();
    private static final String DEFAULT_PRINTER = "default";
    private static final String RESPONSE_FOR_START = "Принтер запущен";
    private static final String RESPONSE_FOR_SEND_DOCUMENT_SUCCESS = "Документ отправлен на печать";
    private static final String RESPONSE_FOR_SEND_DOCUMENT_FAIL = "Принтер не запущен, необходимо запустить принтер," +
            " чтобы отправить документ на печать";
    private static final String RESPONSE_FOR_CANCEL_DOCUMENT = "Документ не будет напечатан, если он находится в очереди печати";

    {
        managers.put(DEFAULT_PRINTER, new PrinterService());
    }

    public String sendDocument(Document document) {
        if (managers.get(DEFAULT_PRINTER).isWorking()) {
            managers.get(DEFAULT_PRINTER).processDocument(document);

            return RESPONSE_FOR_SEND_DOCUMENT_SUCCESS;
        }

        return RESPONSE_FOR_SEND_DOCUMENT_FAIL;
    }

    private void sendStop() {
        managers.get(DEFAULT_PRINTER).setWorking(false);
    }

    public String sendStart() {
        managers.get(DEFAULT_PRINTER).setWorking(true);
        log.info(RESPONSE_FOR_START);
        return RESPONSE_FOR_START;
    }

    public String cancelDocument(String name) {
        log.info("Отмена печати документа с названием: " + name);
        managers.get(DEFAULT_PRINTER).getMarkCanceledDocuments().add(name);
        return RESPONSE_FOR_CANCEL_DOCUMENT;
    }

    public List<Document> getCanceledDocuments() {
        log.info("Завершение печати документов");
        sendStop();
        return managers.get(DEFAULT_PRINTER).getRemovedDocuments();
    }

    public List<Document> getPrintedDocuments(DocumentSort documentSort, String order) {
        log.info("Получение напечатанных документов");
        return getSortedDocuments(managers.get(DEFAULT_PRINTER).getPrintedDocuments(), documentSort, order);
    }

    public List<Document> getUnprintedDocuments(DocumentSort documentSort, String order) {
        log.info("Получение ненапечатанных документов");
        return getSortedDocuments(managers.get(DEFAULT_PRINTER).getCanceledDocuments(), documentSort, order);
    }

    private List<Document> getSortedDocuments(List<Document> documents, DocumentSort documentSort, String order) {
        log.info("Передан тип сортировки: " + documentSort.getName());

        switch (documentSort) {
            case DURATION:
                Collections.sort(documents, new DocumentComparatorByDuration());
                break;
            case NAME:
                Collections.sort(documents, new DocumentComparatorByName());
                break;
            case SIZE:
                Collections.sort(documents, new DocumentComparatorBySize());
                break;
            case TYPE:
                Collections.sort(documents, new DocumentComparatorByType());
                break;
            default:
                break;
        }

        if (order.equals("desc")) {
            Collections.reverse(documents);
            log.info("Используется DESC");
        }

        return documents;
    }

    public long getAverageDurationPrintedDocuments() {
        return managers.get(DEFAULT_PRINTER).getAverageDuration();
    }
}
