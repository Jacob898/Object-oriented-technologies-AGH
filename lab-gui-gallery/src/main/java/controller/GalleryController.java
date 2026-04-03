package controller;


import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;

public class GalleryController {

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView imagesListView;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField imageNameField;

    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<Photo>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });
        imagesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> bindSelectedPhoto((Photo) oldValue, (Photo) newValue)
        );

    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        imagesListView.getSelectionModel().select(0);
        imagesListView.setItems(gallery.getPhotos());

    }

//    private void bindSelectedPhoto(Photo selectedPhoto) {
//        imageNameField.textProperty().bind(selectedPhoto.nameProperty());
//        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
//    }
private void bindSelectedPhoto(Photo oldPhoto, Photo newPhoto) {
        if (oldPhoto != null) {
            imageNameField.textProperty().unbindBidirectional(oldPhoto.nameProperty());
            imageView.imageProperty().unbind();
        }
        if (newPhoto != null) {
            imageNameField.textProperty().bindBidirectional(newPhoto.nameProperty());
            imageView.imageProperty().bind(newPhoto.photoDataProperty());
        } else {
            imageNameField.textProperty().unbind();
            imageView.imageProperty().unbind();
        }


    }

    public void searchButtonClicked(ActionEvent event) {
        PhotoDownloader photoDownloader = new PhotoDownloader();
        galleryModel.clear();
        photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(photo -> galleryModel.addPhoto(photo) );
//                .subscribe(photo -> Platform.runLater(() -> galleryModel.addPhoto(photo) )); -- to zamiast dwóch na górze
    }

}


