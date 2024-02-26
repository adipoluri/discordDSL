package evaluator;

import ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatorTest {

    @Test
    void basicPrintTest(){
        // Input
        Decl new_stmt = new Decl(new Name("x"));
        Set set_stmt = new Set(new Name("x"), new NumberConstant(3));
        Print print_stmt = new Print(new Name("x"));
        Program input_program = new Program(List.of(new_stmt, set_stmt, print_stmt));
        // Expected output
        String output_result = "3\n";
        // Run the evaluator
        Evaluator evaluator = new Evaluator();
        EvaluateResult result = evaluator.evaluate(input_program);
        assertFalse(result.hasError());
        assertEquals(result.getResult(), output_result);
    }

    @Test
    void noDeclTest(){
        // Input
        Set set_stmt = new Set(new Name("x"), new NumberConstant(3));
        Print print_stmt = new Print(new Name("x"));
        Program input_program = new Program(List.of( set_stmt, print_stmt));
        // Run the evaluator
        Evaluator evaluator = new Evaluator();
        EvaluateResult result = evaluator.evaluate(input_program);
        assertTrue(result.hasError());
        List<String> errors = result.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0), "ERROR: Cannot set undefined variable");
    }

    @Test
    void noSetTest(){
        // Input
        Decl new_stmt = new Decl(new Name("x"));
        Set set_stmt = new Set(new Name("x"), new NumberConstant(3));
        Print print_stmt = new Print(new Name("x"));
        Program input_program = new Program(List.of(new_stmt, set_stmt, print_stmt));
        // Run the evaluator
        Evaluator evaluator = new Evaluator();
        EvaluateResult result = evaluator.evaluate(input_program);
        assertTrue(result.hasError());
        List<String> errors = result.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0), "ERROR: Cannot read unintialized variable");
    }


}
