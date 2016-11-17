package MediaCollectie.ui;

import MediaCollectie.UIMain;
import MediaCollectie.data.MediaObject;
import MediaCollectie.util.*;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControllerMain {
    // Weird variable to use to make a handler, and to not duplicate it ... Need a better method than what's currently used >.<
    private boolean handlerSet = false;

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
        String dateTextString = tbSearchImage.getText();

        // If TextField is empty, search based on location otherwise try searching based on the filled in date.
        if(dateTextString.equals("")) searchLocation();
        else searchDate(dateTextString);
    }
    public void lvOnMouseClicked(MouseEvent mouseEvent) {
        if(!handlerSet) {
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MediaObject>() {
                @Override
                public void changed(ObservableValue<? extends MediaObject> observable, MediaObject oldValue, MediaObject newValue) {
                    if(oldValue.equals(newValue)) return;
                    imageView.setImage(new Image(newValue.getFile().getAbsolutePath()));
                }
            });
            handlerSet = true;
        }
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
            //System.out.println("EXCEPTION");
            return;
        }
        //System.out.println("Test: " + location[0] + "," + location[1]);

        // Search for the image.
        ISearch<MediaObject> searcher = new SearchClosest<>(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
        if(UIMain.mediaList.isEmpty()) return;
        imageView.setImage(new Image(searcher.run().getFile().getAbsolutePath()));
    }
    private void searchDate(String dateTextString) {
        // Make sure that the format of the date is right.
        if(dateTextString.charAt(2) != '/' || dateTextString.charAt(5) != '/') {
            System.out.println("ERROR 3");
            return;
        }

        // Splits the text into its various compartments.
        String[] dateText = dateTextString.split("/");
        if(Integer.parseInt(dateText[0]) > 31 || Integer.parseInt(dateText[1]) > 12) {
            System.out.println("Error 2");
            return;
        }

        // Making them integers
        int year = Integer.parseInt(dateText[2]);
        int month = Integer.parseInt(dateText[1]);
        int day = Integer.parseInt(dateText[0]);

        // Getting the date written in the text box.
        Calendar date = Calendar.getInstance();
        date.set(year, month,day);
        Date compareDate = date.getTime();

        // Making sure the list isn't empty.
        if(UIMain.mediaList.isEmpty()) return;

        // Start the searcher.
        ISearch<MediaObject> searcher = new SearchDateList<>(compareDate);
        searcher.setList(UIMain.mediaList);

        // Set the result of the searcher in the Image View.
        imageView.setImage(new Image(searcher.run().getFile().getAbsolutePath()));
        // Set the listView to all the found images.
        listView.setItems(new ObservableListWrapper<>(searcher.getList()));
    }
}