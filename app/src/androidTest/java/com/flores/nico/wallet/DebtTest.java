package com.flores.nico.wallet;

import com.flores.nico.database.Debt;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by nicoflores on 03-11-14.
 */
public class DebtTest extends TestCase {
    private Debt debt;
    private double testAmount;
    private boolean testIncome;
    private String testReceiver;
    private Date testReminder;
    private String testDescription;

    @Override
    public void setUp () throws Exception {
        testAmount = 2500.5;
        testIncome = false;
        testReceiver = "Nicolas";
        testReminder = new Date();
        testDescription = "Debt description";

        debt = new Debt(2500.5, false, "Nicolas", new Date(), "Debt description");
    }

    @Override
    public void tearDown () throws Exception {
        debt = null;
    }

    public void testDebtNotNull () throws Exception {
        Assert.assertNotNull(debt);
    }

    public void testDebtAmount () throws Exception {
        double amount = debt.getAmount();
        Assert.assertEquals(testAmount, amount, 0);
    }

    public void testDebtIncome () throws Exception {
        boolean income = debt.isIncome();
        Assert.assertEquals(testIncome, income);
    }

    public void testDebtReceiver () throws Exception {
        String receiver = debt.getReceiver();
        Assert.assertEquals(testReceiver, receiver);
    }

    public void testDebtReminder () throws Exception {
        Date reminder = debt.getReminder();
        Assert.assertEquals(testReminder, reminder);
    }

    public void testDebtDescription () throws Exception {
        String description = debt.getDescription();
        Assert.assertEquals(testDescription, description);
    }
}
