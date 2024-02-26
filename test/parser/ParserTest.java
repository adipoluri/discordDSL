package parser;

import ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void basicParseTest() {
        // Creating the input
        String input = "new x;\nset x, 3;\nprint x;\n";
        // Creating the expected output
        Decl new_stmt = new Decl(new Name("x"));
        Set set_stmt = new Set(new Name("x"), new NumberConstant(3));
        Print print_stmt = new Print(new Name("x"));
        Program expected_result = new Program(List.of(new_stmt, set_stmt, print_stmt));
        // Create the parser
        Parser parser = new Parser();
        Node result = parser.parse(input);
        // actually test
        assertEquals(expected_result, result);

    }

    @Test
    void whitespaceAgnosticTest() {
        // Creating the inputs
        String input = "new x;\nset x, 3;\nprint x;\n";
        String input_whitespace = "new    x;\nset x,3;\nprint x   ;\n";
        // Create the parser
        Parser parser = new Parser();
        Node result = parser.parse(input);
        Node result_whitespace = parser.parse(input_whitespace);
        // assertions
        assertEquals(result, result_whitespace);
    }

    @Test
    void noDeclParseTest() {
        // Creating the input
        String input = "set x, 3;\nprint x;\n";
        // Creating the expected output
        Set set_stmt = new Set(new Name("x"), new NumberConstant(3));
        Print print_stmt = new Print(new Name("x"));
        Program expected_result = new Program(List.of(set_stmt, print_stmt));
        // Create the parser
        Parser parser = new Parser();
        Node result = parser.parse(input);
        // actually test
        assertEquals(expected_result, result);
    }

}
