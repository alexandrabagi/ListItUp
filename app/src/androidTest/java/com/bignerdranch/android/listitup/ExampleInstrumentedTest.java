package com.bignerdranch.android.listitup;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private ListDB ListDB;
    private List<Item> items;
    private Context appContext;

    @Before
    public void setUp(){
        appContext = InstrumentationRegistry.getTargetContext();
        ListDB = ListDB.get(appContext);
        items = ListDB.getListDB();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        assertEquals("com.bignerdranch.android.listitup", appContext.getPackageName());
    }

    @Test
    public void emptyDB(){
        assertEquals(0,items.size());
    }

    public void fillingDB(){
        items.add(new Item("soap", "ALDI", "3"));
        items.add(new Item("bread", "ALDI", "9"));
        items.add(new Item("Nutella", "ALDI", "3"));
        items.add(new Item("soup", "LIDL", "4"));
    }

    @Test
    public void addingToDB(){
        fillingDB();
        // cause we only add to local list, not to database
        assertEquals((ListDB.getListDB().size() + 4),items.size());
    }

    @Test
    public void testItem(){
        fillingDB();
        Item item = new Item ("chocolate", "LIDL", "22");
        assertEquals("chocolate",item.getWhat());
        assertEquals("LIDL",item.getShop());
        assertEquals("22",item.getQuantity());
        assertEquals("soup",items.get((ListDB.getListDB().size() + 3)).getWhat());
        assertEquals("3",items.get((ListDB.getListDB().size() + 2)).getQuantity());
        assertEquals("ALDI",items.get((ListDB.getListDB().size() + 1)).getShop());
    }

    @Test
    public void deletingFromDB(){
        fillingDB();
        ListDB.deleteItem("soap");
        assertEquals("bread",items.get((ListDB.getListDB().size() + 1)).getWhat());
    }

}
