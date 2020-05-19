package centralbank.printingmanager.job;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.service.impl.PrinterServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProcessDocument implements Runnable {

    private final Document document;
    private final PrinterServiceImpl manager;

    @Override
    public void run() {
        if (manager.isWorking()) {
            if (manager.getMarkCanceledDocuments().contains(document.getName())) {
                addToCanceledDocuments(document);
                manager.getMarkCanceledDocuments().remove(document.getName());
                return;
            }
            addToPrintedDocuments(document);
        } else {
            addToCanceledDocuments(document);
        }
    }

    private void addToCanceledDocuments(Document document) {
        manager.getCanceledDocuments().add(document);
        log.info("Печать документа \"" + document.getName() + "\" отменена");
    }

    private void addToPrintedDocuments(Document document) {
        try {
            log.info("Документ \"" + document.getName() + "\" будет печататься: " + document.getDuration() + " (мс) ");
            Thread.sleep(document.getDuration());
            manager.getPrintedDocuments().add(document);
            log.info("Документ напечатан");
        } catch (InterruptedException e) {
            log.error("Произошла ошибка во время печати документа: " + e.getMessage());
        }
    }
}
