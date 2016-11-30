package MediaCollectie.ui;

import MediaCollectie.UIMain;
import MediaCollectie.data.MediaObject;
import MediaCollectie.util.*;
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
                UIMain.mediaList.add(new MediaObject(f.getName(), (int)f.getTotalSpace(), new Date(f.lastModified()), f.getAbsoluteFile()));

        listView.setItems(new ObservableListWrapper<>(UIMain.mediaList));
    }
    public void bSortImagesOnAction(ActionEvent actionEvent) {
        int selectedID = cbSortImages.getSelectionModel().getSelectedIndex();
        System.out.println(selectedID);
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
        ISort<MediaObject> sorter = new SortDate<>();
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
        ISort<MediaObject> sorter = new SortLightIntensity<>();
        sorter.setList(UIMain.mediaList);
        sorter.run();

        UIMain.mediaList = new ArrayList<>(sorter.getList());
        listView.setItems(new ObservableListWrapper<>(sorter.getList()));
    }

    private void searchLocation() {
        // Get the latitude and longitude value of your current location (needs connection to the web).
        String[] location = HandlerLocation.getLatitudeAndLongitude("");
        // Some control to make sure there are no NullPointerExceptions.
        if(location == null || location.length < 2) {
            return;
        }
        if(UIMain.mediaList.isEmpty()) return;

        // Search for the image.
        ISearch<MediaObject> searcher = new SearchClosest<>(HandlerLocation.D2R(Double.parseDouble(location[0])), HandlerLocation.D2R(Double.parseDouble(location[1])));

        searcher.setList(UIMain.mediaList);
        MediaObject search = searcher.run();
        System.out.println(search);
        imageView.setImage(new Image(search.getFile().toURI().toString()));
    }
    private void searchDate(LocalDate date) {
        if(UIMain.mediaList.isEmpty()) return;

        ISearch<MediaObject> searcher = new SearchDateList<>(date);
        searcher.setList(UIMain.mediaList);

        MediaObject search = searcher.run();
        imageView.setImage(new Image(search.getFile().toURI().toString()));
    }
}