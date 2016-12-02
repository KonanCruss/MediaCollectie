package MediaCollectie.ui;

import MediaCollectie.UIMain;
import MediaCollectie.data.MediaObject;
import MediaCollectie.util.search.ISearch;
import MediaCollectie.util.search.SearchDateBinary;
import MediaCollectie.util.search.SearchLocation;
import MediaCollectie.util.sort.ISort;
import MediaCollectie.util.sort.SortDate;
import MediaCollectie.util.sort.SortMedian;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ControllerMain {
    public Button bSelectFolder;
    public ImageView imageView;
    public Button bSortImages;
    public Button bSearchImage;
    public ListView<MediaObject> listView;
    public ChoiceBox cbSortImages;
    public DatePicker getDate;

    public void bSelectFolderOnAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open folder with pictures");

        File selectedFile = directoryChooser.showDialog(UIMain.UIStage);
        if(selectedFile == null || selectedFile.listFiles() == null) return;

        if(!UIMain.mediaList.isEmpty()) {
            UIMain.mediaList.clear();
            listView.getItems().clear();
        }

        for(File f : selectedFile.listFiles())
            if(f.isFile())
                UIMain.mediaList.add(new MediaObject(f.getName(), new Date(f.lastModified()), f.getAbsoluteFile()));

        listView.setItems(new ObservableListWrapper<>(UIMain.mediaList));
    }
    public void bSortImagesOnAction(ActionEvent actionEvent) {
        int selectedID = cbSortImages.getSelectionModel().getSelectedIndex();
        System.out.println(selectedID);
        if(UIMain.mediaList.isEmpty()) return;
        switch (selectedID) {
            case 0:
                sortDatum();
                break;
            case 1:
                sortLightIntensity();
                break;
            default:
                break;
        }
    }
    public void bSearchImageOnAction(ActionEvent actionEvent) {
        // The text in the TextField.
        LocalDate date = getDate.getValue();

        // If TextField is empty, search based on location otherwise try searching based on the filled in date.
        if(date == null) searchLocation();
        else searchDate(date);
    }
    public void lvOnMouseClicked(MouseEvent mouseEvent) {
        //System.out.println(listView.getSelectionModel().getSelectedItem().getFile().toURI().toString());
        Image test = new Image(listView.getSelectionModel().getSelectedItem().getFile().toURI().toString());
        imageView.setImage(test);
    }

    private void sortDatum() {
        if(UIMain.mediaList.isEmpty()) return;
        ISort<MediaObject> sorter = new SortDate<>(MediaObject.getLocalDateMap(UIMain.mediaList));

        UIMain.mediaList = new ArrayList<>(sorter.run());
        listView.getItems().clear();
        listView.setItems(new ObservableListWrapper<>(UIMain.mediaList));
    }
    private void sortLightIntensity() {
        if(UIMain.mediaList.isEmpty()) return;
        ISort<MediaObject> sorter = new SortMedian<>(UIMain.mediaList);

        UIMain.mediaList = new ArrayList<>(sorter.run());
        listView.getItems().clear();
        listView.setItems(new ObservableListWrapper<>(UIMain.mediaList));
    }

    private void searchLocation() {
        ISearch<MediaObject> search = new SearchLocation<>(MediaObject.getLocationMap(UIMain.mediaList));
        imageView.setImage(new Image(search.run().getFile().toURI().toString()));
    }
    private void searchDate(LocalDate date) {
        ISearch<MediaObject> search = new SearchDateBinary<MediaObject>(MediaObject.getLocalDateMap(UIMain.mediaList), date);
        MediaObject result = search.run();
        if(result != null) imageView.setImage(new Image(result.getFile().toURI().toString()));
    }
}