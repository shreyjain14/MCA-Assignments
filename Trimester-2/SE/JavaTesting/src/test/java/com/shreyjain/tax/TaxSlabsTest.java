package com.shreyjain.tax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaxSlabsTest {

    private final TaxSlabs taxSlabs = new TaxSlabs();

    @Test
    public void testInvalidIncomeAndAge() {
        assertEquals(-1, taxSlabs.getTaxSlab(17, 200000), "Invalid age below 18 should return -1");
        assertEquals(-1, taxSlabs.getTaxSlab(25, -10000), "Negative income should return -1");
    }

    @Test
    public void testTaxSlabsForBelow60() {
        assertEquals(0, taxSlabs.getTaxSlab(25, 250000), "Income up to 2,50,000 should have 0% tax");
        assertEquals(5, taxSlabs.getTaxSlab(30, 300000), "Income 2,50,001 to 5,00,000 should have 5% tax");
        assertEquals(10, taxSlabs.getTaxSlab(45, 600000), "Income 5,00,001 to 7,50,000 should have 10% tax");
        assertEquals(15, taxSlabs.getTaxSlab(35, 800000), "Income 7,50,001 to 10,00,000 should have 15% tax");
        assertEquals(20, taxSlabs.getTaxSlab(29, 1100000), "Income 10,00,001 to 12,50,000 should have 20% tax");
        assertEquals(25, taxSlabs.getTaxSlab(50, 1300000), "Income 12,50,001 to 15,00,000 should have 25% tax");
        assertEquals(30, taxSlabs.getTaxSlab(40, 1600000), "Income above 15,00,000 should have 30% tax");
    }

    @Test
    public void testTaxSlabsForSeniorCitizens() {
        assertEquals(0, taxSlabs.getTaxSlab(65, 300000), "Income up to 3,00,000 should have 0% tax for seniors");
        assertEquals(5, taxSlabs.getTaxSlab(70, 400000), "Income 3,00,001 to 5,00,000 should have 5% tax for seniors");
        assertEquals(10, taxSlabs.getTaxSlab(61, 600000), "Income 5,00,001 to 7,50,000 should have 10% tax for seniors");
        assertEquals(15, taxSlabs.getTaxSlab(75, 800000), "Income 7,50,001 to 10,00,000 should have 15% tax for seniors");
        assertEquals(20, taxSlabs.getTaxSlab(60, 1100000), "Income 10,00,001 to 12,50,000 should have 20% tax for seniors");
        assertEquals(25, taxSlabs.getTaxSlab(68, 1300000), "Income 12,50,001 to 15,00,000 should have 25% tax for seniors");
        assertEquals(30, taxSlabs.getTaxSlab(72, 1600000), "Income above 15,00,000 should have 30% tax for seniors");
    }

    @Test
    public void testTaxSlabsForSuperSeniorCitizens() {
        assertEquals(0, taxSlabs.getTaxSlab(85, 500000), "Income up to 5,00,000 should have 0% tax for super seniors");
        assertEquals(20, taxSlabs.getTaxSlab(90, 800000), "Income 5,00,001 to 10,00,000 should have 20% tax for super seniors");
        assertEquals(30, taxSlabs.getTaxSlab(82, 1100000), "Income above 10,00,000 should have 30% tax for super seniors");
    }
}
