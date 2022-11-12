package com.swh.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
public class ParseURL {
     
    public static void main(String[] args) throws IOException {
         
        // Create a URL
        try {
             
            URL url = new URL("http://192.168.0.11:4567/");
            
             
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
         
    }
 
}