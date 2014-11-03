package com.flores.nico.wallet;

import com.flores.nico.database.Category;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by nicoflores on 03-11-14.
 */
public class CategoryTest extends TestCase {
    private Category category;
    private String testName;
    private String testDescription;

    @Override
    public void setUp () throws Exception {
        testName = "Category Test";
        testDescription = "Description Test";
        category = new Category("Category Test", "Description Test");
    }

    @Override
    public void tearDown () throws Exception {
        category = null;
    }

    public void testCategoryNotNull () throws Exception {
        Assert.assertNotNull(category);
    }

    public void testCategoryConstructor () throws Exception {
        String name = category.getName();
        Assert.assertEquals(testName, name);

        String description = category.getDescription();
        Assert.assertEquals(testDescription, description);
    }
}
