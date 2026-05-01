package de.geheimagentnr1.rapid_leaf_decay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RapidLeafDecayTest {

    @Test
    void modIdIsValid() {

        String modId = "rapid_leaf_decay";
        assertTrue( modId.matches( "[a-z][a-z0-9_]{1,63}" ) );
    }
}
