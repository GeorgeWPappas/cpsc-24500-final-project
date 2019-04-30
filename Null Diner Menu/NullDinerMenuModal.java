/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file is liscensed under the BSD-3-Clause
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 *****************************************************************************/

 import java.util.ArrayList;

class NullDinerMenuModel extends HttpRequest {

    private String indexURL = "https://thunderbird-data.azurewebsites.net/url-list.json";
    
    private ArrayList<NullDinerMenuContact> contactList;

    NullDinerMenuModel() {
        contactList = new ArrayList<NullDinerMenuContact>();
    }

    public Boolean LoadIndex() {
        Boolean returnValue = false;
        if (readURL(indexURL)) {
            // Look through the index JSON file for each URL and then creat a new Contact object.
            for (String line : urlContent) {
                String[] subString = line.split("\"");
                // The substring after the 11 double-quote should be the URL.
                if (subString.length >= 11) {
                    if (subString[11].indexOf("https://") > -1) {
                        contactList.add(new NullDinerMenuContact(subString[11]));
                        returnValue = true;
                    }
                }
            }            
        }
        
        return returnValue; 
    }

    public void LoadContacts() {
        long start = System.currentTimeMillis();
        int menuItemsLoaded = 0;
        for (NullDinerMenuContact ndmC : contactList) {
            ndmC.Load();
            menuItemsLoaded++;
        }

        System.out.println("Menu Items Loaded: " + menuItemsLoaded);
        System.out.println("Menu Items Loaded Elapsed Time: " + (System.currentTimeMillis()-start) + " ms\n");
    }

    public void LoadContactsThreaded() {
        long start = System.currentTimeMillis(); 
        int menuItemsLoaded = 0;

        ArrayList<Thread> threadList = new ArrayList<Thread>();
        for (NullDinerMenuContact ndmC : contactList) {
            threadList.add(new Thread(ndmC));
        }        

        for (Thread t: threadList) {
            t.start();
            menuItemsLoaded++;
        }

        try {
            for (Thread t: threadList) {
                t.join();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        System.out.println("Contacts Loaded: " + menuItemsLoaded);
        System.out.println("LoadContacts Elapsed Time = " + (System.currentTimeMillis()-start) + " ms\n");
    }
    
    public String toString() {
        // The super class contains the Index of contacts.
        String returnString = "Index:\n" + super.toString();

        returnString = returnString + "\nPrinting Contacts:\n";
        for (NullDinerMenuContact contact : contactList) {
            returnString = returnString + contact + "\n";
        } 
        return returnString;
    }

    public NullDinerMenuContact findItemInMenu(int seat) {
        NullDinerMenuContact returnContact = null; 
        for (NullDinerMenuContact contact : contactList) {        
        }
        return returnContact; 
    } 
}