package me.shreyjain.joobseekerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Controller controller;

    // Add operation tests
    @Test
    void testAdd_PositiveNumbers() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "5")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("8"));
    }

    @Test
    void testAdd_NegativeNumbers() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "-10")
                        .param("b", "-5"))
                .andExpect(status().isOk())
                .andExpect(content().string("-15"));
    }

    @Test
    void testAdd_MixedNumbers() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "10")
                        .param("b", "-3"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

    @Test
    void testAdd_Zero() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "0")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testAdd_MissingParameter() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAdd_InvalidParameter() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "abc")
                        .param("b", "5"))
                .andExpect(status().isBadRequest());
    }

    // Multiply operation tests
    @Test
    void testMultiply_PositiveNumbers() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "4")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("20"));
    }

    @Test
    void testMultiply_NegativeNumbers() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "-3")
                        .param("b", "-4"))
                .andExpect(status().isOk())
                .andExpect(content().string("12"));
    }

    @Test
    void testMultiply_MixedNumbers() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "-6")
                        .param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("-12"));
    }

    @Test
    void testMultiply_ByZero() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "10")
                        .param("b", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testMultiply_ByOne() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "7")
                        .param("b", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

    @Test
    void testMultiply_MissingParameter() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMultiply_InvalidParameter() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "5")
                        .param("b", "xyz"))
                .andExpect(status().isBadRequest());
    }

    // Divide operation tests
    @Test
    void testDivide_PositiveNumbers() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "10")
                        .param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testDivide_NegativeNumbers() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "-12")
                        .param("b", "-3"))
                .andExpect(status().isOk())
                .andExpect(content().string("4"));
    }

    @Test
    void testDivide_MixedNumbers() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "-15")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("-5"));
    }

    @Test
    void testDivide_ZeroNumerator() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "0")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testDivide_ByZero_ThrowsException() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "10")
                        .param("b", "0"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDivide_IntegerDivision() throws Exception {
        // Testing integer division behavior (7/3 = 2 in integer division)
        mockMvc.perform(get("/divide")
                        .param("a", "7")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void testDivide_MissingParameter() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDivide_InvalidParameter() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "abc")
                        .param("b", "5"))
                .andExpect(status().isBadRequest());
    }

    // Unit tests for direct method calls (without HTTP layer)
    @Test
    void testAddMethod_Direct() {
        Integer result = controller.add(3, 7);
        assertEquals(10, result);
    }

    @Test
    void testMultiplyMethod_Direct() {
        Integer result = controller.multiply(4, 6);
        assertEquals(24, result);
    }

    @Test
    void testDivideMethod_Direct() {
        Integer result = controller.divide(15, 3);
        assertEquals(5, result);
    }

    @Test
    void testDivideMethod_Direct_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.divide(10, 0)
        );
        assertEquals("b cannot be 0", exception.getMessage());
    }

    // Edge case tests
    @Test
    void testLargeNumbers_Add() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", String.valueOf(Integer.MAX_VALUE - 1))
                        .param("b", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void testLargeNumbers_Multiply() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "1000")
                        .param("b", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000000"));
    }

    @Test
    void testMinValues() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", String.valueOf(Integer.MIN_VALUE))
                        .param("b", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Integer.MIN_VALUE)));
    }
}