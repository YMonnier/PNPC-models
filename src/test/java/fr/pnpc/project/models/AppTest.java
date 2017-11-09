package fr.pnpc.project.models;

import org.junit.jupiter.api.*;
import org.junit.runners.Suite;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */

public class AppTest {

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void superTest() {
        Assertions.assertEquals(true, true);
    }

    @AfterAll
    static void tearDownAll() {
    }

}