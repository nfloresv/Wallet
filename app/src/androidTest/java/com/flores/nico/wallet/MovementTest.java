package com.flores.nico.wallet;

import com.flores.nico.database.Category;
import com.flores.nico.database.Movement;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by nicoflores on 03-11-14.
 */
public class MovementTest extends TestCase {
    private Movement movement;
    private double testAmount;
    private Category testCategory;
    private boolean testIncome;
    private Date testDate;
    private String testDescription;
    private String testFilePath;

    public MovementTest () {
        this.testAmount = 2500.5;
        this.testCategory = new Category("Movement Category", "Movement Category description");
        this.testIncome = true;
        this.testDate = new Date();
        this.testDescription = "Movement description";
        this.testFilePath = "path/to/file";
    }

    @Override
    public void setUp () throws Exception {
        movement = new Movement(2500.5, testCategory, true, testDate, "Movement description",
                "path/to/file");
    }

    @Override
    public void tearDown () throws Exception {
        movement = null;
    }

    public void testMovementNotNull () throws Exception {
        Assert.assertNotNull(movement);
    }

    public void testCategoryNotNull () throws Exception {
        Category category = movement.getCategory();
        Assert.assertNotNull(category);
    }

    public void testMovementAmount () throws Exception {
        double amount = movement.getAmount();
        Assert.assertEquals(testAmount, amount);
    }

    public void testMovementCategory () throws Exception {
        Category category = movement.getCategory();
        Assert.assertEquals(testCategory, category);
    }

    public void testMovementIncome () throws Exception {
        boolean income = movement.isIncome();
        Assert.assertEquals(testIncome, income);
    }

    public void testMovementDate () throws Exception {
        Date date = movement.getMovement_date();
        Assert.assertEquals(testDate, date);
    }

    public void testMovementDescription () throws Exception {
        String description = movement.getDescription();
        Assert.assertEquals(testDescription, description);
    }

    public void testMovementFilePath () throws Exception {
        String filePath = movement.getFilePath();
        Assert.assertEquals(testFilePath, filePath);
    }

    public void testCategoryName () throws Exception {
        Category category = movement.getCategory();
        String name = category.getName();
        Assert.assertEquals("Movement Category", name);
    }
}
