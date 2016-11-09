package com.apexTOP.ui;

import com.apexTOP.UIMain;
import com.apexTOP.data.MediaObject;

import java.util.ArrayList;
import java.util.Date;

import com.apexTOP.util.ISort;
import com.apexTOP.util.SortDatum;
import com.apexTOP.util.SortInsertion;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ControllerMain {
    public Button bSelectFolder;
    public ImageView imageView;
    public Button bSortImages;
    public TextField tbSearchImage;
    public Button bSearchImage;
    public ListView<MediaObject> listView;
    public ChoiceBox cbSortImages;

    public void bSelectFolderOnAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open folder with pictures");

        File selectedFile = directoryChooser.showDialog(UIMain.UIStage);
        if(selectedFile == null || selectedFile.listFiles() == null) return;

        for(File f : selectedFile.listFiles())
            if(f.isFile())
                UIMain.mediaList.add(new MediaObject(f.getName(), (int)f.getTotalSpace(), new Date(f.lastModified()), f.getAbsoluteFile()));

        listView.setItems(new ObservableListWrapper<>(UIMain.mediaList));
    }

    public void bSortImagesOnAction(ActionEvent actionEvent) {
        int selectedID = cbSortImages.getSelectionModel().getSelectedIndex();
        if(UIMain.mediaList.isEmpty()) return;
        switch (selectedID) {
            case 0:
                sortDatumInsertion();
                break;
            case 1:
                sortDatum();
                break;
            case 2:
                sortLightIntensity();
                break;
            default:
                break;
        }
    }

    public void bSearchImageOnAction(ActionEvent actionEvent) {
        
    }

    private void sortDatum() {
        ISort<MediaObject> sorter = new SortDatum<>();
        sorter.setList(UIMain.mediaList);
        sorter.run();

        UIMain.mediaList = new ArrayList<>(sorter.getList());
        listView.setItems(new ObservableListWrapper<>(sorter.getList()));
    }
    private void sortDatumInsertion() {
        ISort<MediaObject> sorter = new SortInsertion<>();
        sorter.setList(UIMain.mediaList);
        sorter.run();

        UIMain.mediaList = new ArrayList<>(sorter.getList());
        listView.setItems(new ObservableListWrapper<>(sorter.getList()));
    }
    private void sortLightIntensity() {
        System.out.println("Not implemented yet! Can't find a Java Library to get Image Filtering working");
    }
}
