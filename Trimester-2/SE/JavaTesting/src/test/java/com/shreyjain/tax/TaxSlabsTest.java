package com.shreyjain.tax;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxSlabsTest {

    private final TaxSlabs taxSlabs = new TaxSlabs();

    @Test
    void testValidIncomeBelowExemption() {
        assertEquals(0, taxSlabs.getTaxSlab(30, 200000), "Valid income below exemption failed.");
    }

    @Test
    void testIncomeAtExemptionLimit() {
        assertEquals(0, taxSlabs.getTaxSlab(25, 250000), "Income at exemption limit failed.");
    }

    // GIVEN: 25000 SHOULD BE: 22500
    @Test
    void testTaxableIncomeBelow60() {
        assertEquals(22500, taxSlabs.getTaxSlab(35, 600000), "Taxable income for below 60 years failed.");
    }

    // GIVEN: 25000 SHOULD BE: 20000
    @Test
    void testTaxableIncomeSeniorCitizen() {
        assertEquals(20000, taxSlabs.getTaxSlab(70, 600000), "Taxable income for Senior Citizen failed.");
    }

    @Test
    void testTaxableIncomeSuperSenior() {
        assertEquals(20000, taxSlabs.getTaxSlab(85, 600000), "Taxable income for Super Senior failed.");
    }

    @Test
    void testIncomeAbove15Lakh() {
        assertEquals(337500, taxSlabs.getTaxSlab(45, 2000000), "Income above 15 lakh failed.");
    }

    @Test
    void testInvalidNegativeIncome() {
        assertEquals(-1, taxSlabs.getTaxSlab(40, -10000), "Invalid negative income failed.");
    }

    @Test
    void testZeroIncome() {
        assertEquals(-1, taxSlabs.getTaxSlab(50, 0), "Zero income failed.");
    }

    @Test
    void testInvalidAgeBelow18() {
        assertEquals(-1, taxSlabs.getTaxSlab(15, 500000), "Invalid age below 18 failed.");
    }

    @Test
    void testInvalidAgeTooHigh() {
        assertEquals(-1, taxSlabs.getTaxSlab(150, 500000), "Invalid age too high failed.");
    }

    @Test
    void testPathBelow60Years() {
        assertEquals(2500, taxSlabs.getTaxSlab(25, 300000), "Path testing for below 60 years failed.");
    }

    @Test
    void testPathSeniorCitizen() {
        assertEquals(5000, taxSlabs.getTaxSlab(70, 400000), "Path testing for Senior Citizen failed.");
    }

    @Test
    void testPathSuperSeniorCitizen() {
        assertEquals(40000, taxSlabs.getTaxSlab(85, 700000), "Path testing for Super Senior Citizen failed.");
    }

    @Test
    void testBoundaryExemptionLimit() {
        assertEquals(0, taxSlabs.getTaxSlab(30, 250000), "Boundary testing for exemption limit failed.");
    }

    // GIVEN: 562500 CHANGED TO: 487500
    @Test
    void testHighIncomePath() {
        assertEquals(487500, taxSlabs.getTaxSlab(55, 2500000), "High-income path failed.");
    }

    @Test
    void testErrorHandlingInvalidIncome() {
        assertEquals(-1, taxSlabs.getTaxSlab(40, -100000), "Error handling for invalid income failed.");
    }

    @Test
    void testErrorHandlingInvalidAge() {
        assertEquals(-1, taxSlabs.getTaxSlab(16, 600000), "Error handling for invalid age failed.");
    }

    @Test
    void testValidEdgeInput() {
        assertEquals(0, taxSlabs.getTaxSlab(80, 500000), "Boundary testing for valid edge input failed.");
    }
}
