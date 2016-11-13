package com.apexTOP;

import com.apexTOP.data.MediaObject;
import com.apexTOP.util.ISort;
import com.apexTOP.util.SortDatum;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String mes = "";
        ArrayList<MediaObject> mediaColectie = new ArrayList<>();
        boolean isSorted = false;

        while(true) {
            System.out.println("Which action do you want to perform");
            try {
                mes = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading the message");
                return;
            }

            switch(mes) {
                case "Data Collection":
                    System.out.println("Give the path to the folder you want to extract in the collection");
                    try {
                        mes = reader.readLine();
                        File folder = new File(mes);
                        System.out.println("Amount of files detected: " + folder.list().length);
                    } catch (IOException e) {
                        System.err.println("Error while reading the message");
                        return;
                    }
                    break;
                case "Save Collection":
                    System.out.println("Give the path to the folder you want to save the collection of");
                    try {
                        mes = reader.readLine();
                        File folder = new File(mes);
                        for (File media : folder.listFiles()) {
                            if(media.isFile()) {
                                mediaColectie.add(new MediaObject(media.getName(), (int)media.getTotalSpace(), new Date(media.lastModified()), media));
                                System.out.println(media.getName());
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error while reading the message");
                        return;
                    }
                    if(isSorted) isSorted = false;
                    break;
                case "Sort List by Date":
                    if(!isSorted) {
                        mediaColectie = sortColectie(mediaColectie, new SortDatum());
                        isSorted = true;
                    }
                    break;
                case "Search Picture by Date":
                    if(!isSorted) {
                        mediaColectie = sortColectie(mediaColectie, new SortDatum());
                        isSorted = true;
                    }
                    System.out.println("Date:");
                    try {
                        mes = reader.readLine();
                        Date searchDate = new Date(Long.parseLong(mes));

                    } catch (IOException e) {
                        System.err.println("Error while reading the message");
                        return;
                    }
                    break;
                case "Quit":
                case "q":
                case "Q":
                    return;
                case "Help":
                case "H":
                case "h":
                default:
                    System.out.println("Possible actions:");
                    System.out.println("Help");
                    System.out.println("Data Collection");
                    System.out.println("Save Collection");
                    System.out.println("Sort List by Date");
                    System.out.println("Search Picture by Date");
                    System.out.println("Quit");
                    break;
            }

            System.out.println("\n");
        }

    }

    @SuppressWarnings("unchecked")
    private static ArrayList<MediaObject> sortColectie(ArrayList<MediaObject> mediaColectie, ISort sortable) {
        sortable.setList(mediaColectie);
        sortable.run();
        return sortable.getList();
    }
}