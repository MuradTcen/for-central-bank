package centralbank.printingmanager.service.impl;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.job.GetCanceledDocuments;
import centralbank.printingmanager.job.ProcessDocument;
import centralbank.printingmanager.service.PrinterService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Data
@Slf4j
public class PrinterServiceImpl implements PrinterService {

    private final List<Document> printedDocuments = new ArrayList<>();
    private List<Document> canceledDocuments = new ArrayList<>();
    // Список названий документов, которые не должны быть приняты к печати
    private List<String> markCanceledDocuments = Collections.synchronizedList(new ArrayList<>());

    private volatile boolean working = true;
    private ExecutorService executor = Executors.newFixedThreadPool(COUNT_OF_THREADS);

    private static final int COUNT_OF_THREADS = 1;
    private static final long WAITING_TIME = 1000;


    public void processDocument(Document document) {
        executor.execute(new ProcessDocument(document, this));
    }

    public List<Document> getRemovedDocuments() {
        Future<List<Document>> task = executor.submit(new GetCanceledDocuments(this));

        log.info("Получение ненапечатанных документы");

        try {
            return task.get(WAITING_TIME, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logError(e);
        }

        return null;
    }

    private static void logError(Exception e) {
        log.error("Произошла ошибка при получении ненапечатанных документов " + e.getMessage());
    }

    public long getAverageDuration() {
        if (printedDocuments.isEmpty()) {
            return 0L;
        }

        long result = 0;
        for (Document document : printedDocuments) {
            result += document.getDuration();
        }

        result /= printedDocuments.size();
        log.info("Средняя продолжительно печати напечатанных документов: " + result);
        log.info("Количество напечатанных документов: " + printedDocuments.size());

        return result;
    }

}
