package centralbank.printingmanager.entity.comparator;

import centralbank.printingmanager.entity.Document;

import java.util.Comparator;

public class DocumentComparatorBySize implements Comparator<Document> {
    @Override
    public int compare(Document document, Document t1) {
        // Для примера сравниваем по периметру
        int totalOfDimensionsDocument = document.getSize().getHeight() + document.getSize().getWidth();
        int totalOfDimensionsT1 = t1.getSize().getHeight() + t1.getSize().getWidth();

        if (totalOfDimensionsDocument > totalOfDimensionsT1) {
            return 1;
        } else if (totalOfDimensionsDocument < totalOfDimensionsT1) {
            return -1;
        } else {
            return 0;
        }
    }
}
