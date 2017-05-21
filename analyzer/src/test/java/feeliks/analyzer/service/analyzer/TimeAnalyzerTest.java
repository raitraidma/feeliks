package feeliks.analyzer.service.analyzer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeAnalyzerTest {
    private TimeAnalyzer timeAnalyzer;

    @Before
    public void setUp() throws Exception {
        timeAnalyzer = new TimeAnalyzer(0, null);
    }

    @Test
    public void belongsToAnalyzer() throws Exception {
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis kell on?"));
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis kell praegu on?"));
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis päev praegu on?"));
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis päev täna on?"));
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis päev homme on?"));
        assertTrue(timeAnalyzer.belongsToAnalyzer("Kuule, mis aasta praegu on?"));
        assertFalse(timeAnalyzer.belongsToAnalyzer("Kuule, mis sa teed?"));
    }
}