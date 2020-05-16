package centralbank.printingmanager.entity.comparator;

import centralbank.printingmanager.entity.Document;

import java.util.Comparator;

public class DocumentComparatorByType implements Comparator<Document> {
    @Override
    public int compare(Document document, Document t1) {
        if (document.getType().getOrdinal() > t1.getType().getOrdinal()) {
            return 1;
        } else if (document.getType().getOrdinal() < t1.getType().getOrdinal()) {
            return -1;
        } else {
            return 0;
        }
    }
}
