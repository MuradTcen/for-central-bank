package centralbank.printingmanager.entity.comparator;

import centralbank.printingmanager.entity.Document;

import java.util.Comparator;

public class DocumentComparatorByName implements Comparator<Document> {
    @Override
    public int compare(Document document, Document t1) {
        return document.getName().compareTo(t1.getName());
    }
}
