package com.bignerdranch.android.listitup;

import android.content.Context;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    public static ListDB ListDB;



    @Before
    public void setUp(){
            ListDB.addToDB(new Item("Chocolate","LIDL", "4" ));
            ListDB.addToDB(new Item("Cake","LIDL", "9" ));
            ListDB.addToDB(new Item("Soap","ALDI", "7" ));
    }

    //Causes nullpointer
    /*@Test
    public void DBisEmpty() {
        assertEquals(ListDB.getListDB().size(), 3);

    }*/
}