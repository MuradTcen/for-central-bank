package centralbank.printingmanager.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentSize {

    A0(1, 1),
    A1(1, 2),
    A2(5, 10),
    A3(10, 20),
    A4(20, 40),
    A5(40, 80);

    private final int width;
    private final int height;

}
