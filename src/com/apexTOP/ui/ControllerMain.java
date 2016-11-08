package com.apexTOP.ui;

import com.apexTOP.UIMain;
import com.apexTOP.data.MediaObject;
import java.util.Date;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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

    }

    public void bSearchImageOnAction(ActionEvent actionEvent) {

    }
}
