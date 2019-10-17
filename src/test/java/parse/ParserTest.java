package parse;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void splitArguments_test1() {
        final String[] expected = {"!test", "a", "b b", "c"};
        final String[] actual =  this.parser.splitArguments("!test a \"b b\" c d");
        assertArrayEquals(expected, actual);
    }
}