package centralbank.printingmanager.service;

import centralbank.printingmanager.entity.Document;

import java.util.List;

public interface PrinterService {
    void processDocument(Document document);

    List<Document> getRemovedDocuments();

    long getAverageDuration();
}
