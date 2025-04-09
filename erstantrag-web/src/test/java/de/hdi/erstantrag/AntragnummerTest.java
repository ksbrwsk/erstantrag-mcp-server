package de.hdi.erstantrag;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

public class AntragnummerTest {

    @Test
    void testantragnummer() {
        SecureRandom random = new SecureRandom();
        random.setSeed(100);
        int nextInt = random.nextInt();
        String antragnummer = "AN-00-"+ StringUtils.leftPad(Integer.toString(nextInt), 4, "0");
        System.out.println(antragnummer);
    }
}
