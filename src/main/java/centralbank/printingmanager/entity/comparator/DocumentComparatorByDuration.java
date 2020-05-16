package centralbank.printingmanager.entity.comparator;

import centralbank.printingmanager.entity.Document;

import java.util.Comparator;

public class DocumentComparatorByDuration implements Comparator<Document> {
    @Override
    public int compare(Document document, Document t1) {
        if (document.getDuration() > t1.getDuration()) {
            return 1;
        } else if (document.getDuration() < t1.getDuration()) {
            return -1;
        } else {
            return 0;
        }
    }
}
