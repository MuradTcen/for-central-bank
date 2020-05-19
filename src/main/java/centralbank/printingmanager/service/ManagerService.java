package centralbank.printingmanager.service;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.entity.DocumentSort;

import java.util.List;


public interface ManagerService {
    String sendDocument(Document document);

    void sendStop();

    String sendStart();

    String cancelDocument(String name);

    List<Document> stopPrintingAndGetCanceledDocuments();

    List<Document> getPrintedDocuments(DocumentSort documentSort, String order);

    List<Document> getUnprintedDocuments(DocumentSort documentSort, String order);

    List<Document> getSortedDocuments(List<Document> documents, DocumentSort documentSort, String order);

    long getAverageDurationPrintedDocuments();

    Document getDocumentByType(String type);

    void clear();
}

