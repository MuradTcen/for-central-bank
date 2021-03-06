package centralbank.printingmanager.job;

import centralbank.printingmanager.entity.Document;
import centralbank.printingmanager.service.impl.PrinterServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class GetCanceledDocuments implements Callable<List<Document>> {

    private final PrinterServiceImpl manager;

    @Override
    public List<Document> call() throws Exception {
        return manager.getCanceledDocuments();
    }
}
