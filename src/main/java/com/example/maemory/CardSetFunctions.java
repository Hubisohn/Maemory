package com.example.maemory;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class CardSetFunctions {
	
	public static void convertToCardSetWithDialog(Integer width, Integer height, Integer size) throws Exception{
		
		DirectoryChooser chooser = new DirectoryChooser();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Important!");
		alert.setContentText("The directory you select must contain an amount of image files equal to "+ (int) (Math.pow(size, 2) / 2) +" + 1.\nThe image to be used as the back of every card must contain \"back\" or \"background\" in its filename, the filenames of the other images do not matter.");
		alert.showAndWait();
		chooser.setTitle("choose directory with images");
		File file = chooser.showDialog(null);
		
		if (file == null) {
			
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("no directory selected");
			alert.show();
			throw new NullPointerException("no directory selected");
			
		}else if (!file.isDirectory()) {
			
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("you must select a directory");
			alert.show();
			throw new NotDirectoryException("you must select a directory");
			
		}else {
			
			convertToCardSetWithoutDialog(width, height, file.listFiles(), size);
			
		}
		
	}
	
	public static void convertToCardSetWithoutDialog(final Integer width, final Integer height, File[] files, Integer size) throws Exception {
		
		if (size % 2 != 0) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("the size must be an even value");
			alert.show();
			throw new IllegalArgumentException("the size must be an even value");
			
		}if ((width != null && height != null) && (width <= 0 || height <= 0)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("width and height must be greater than 0");
			alert.show();
			throw new IllegalStateException("width and height must be greater than 0");
		}

		ArrayList<Image> images = new ArrayList<>();
		final Image[] background = {null};
		final Exception[] ex = {null};
		final int[] counter = {0};
		
		if (files == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("no Files provided");
			alert.show();
			throw new NullPointerException("no Files provided");
		}
		
		Stream.of(Objects.requireNonNull(files)).forEach((f) -> {
			
			try {
				
				Image image;
				
				if (width == null || height == null) {
					image = new Image(f.toURI().toURL().toString());
				}else {
					image = new Image(f.toURI().toURL().toString(), width, height, false, false);
				}
				
				if (!image.isError()) {
					
					if (f.getName().toLowerCase().contains("background") || f.getName().toLowerCase().contains("back")) {
						
						background[0] = image;

						
					} else {
						
						images.add(image);
						
					}
					
					counter[0] = counter[0] + 1;
					
				}
				
			} catch (MalformedURLException e) {
				ex[0] = e;
				throw new IllegalStateException(e);
			} catch (Exception e) {
				ex[0] = e;
				throw e;
			}
			
		});
		
		if (ex[0] != null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(ex[0].getMessage());
			alert.show();
			throw ex[0];
			
		} if (counter[0] == 0) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No Files provided are images");
			alert.show();
			throw new IllegalStateException("No Files provided are images");
			
		} if (Math.pow(size,2) / 2 != images.size()) {
			
			
			images.removeIf(Image::isError);
			
			if (Math.pow(size,2) / 2 > images.size()) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Not Enough images provided");
				alert.show();
				return;
				
			}else {
				
				while (Math.pow(size,2) / 2 != images.size()) {
					images.remove(images.size()-1);
				}
				
			}
			
		} if (background[0] == null) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			alert.show();
			throw new IllegalArgumentException("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			
		}
		
		int i = 0;
		String s;
		Stage stage = new Stage();
		TextField field = new TextField();
		Button button = new Button("OK");
		VBox vBox = new VBox(new Label("please type the name of the new CardSet"),field,button);
		Scene scene = new Scene(vBox,240,120);
		button.setOnAction((a) -> stage.close());
		vBox.setSpacing(15);
		field.setPromptText("name of the new CardSet");
		stage.setScene(scene);
		stage.setTitle("name of the new CardSet");
		stage.showAndWait();
		
		if (Objects.equals(field.getText(), "")) {
			
			s = "new Set";
			
		}else {
		
			s = field.getText();
			
		}
		
		if(new File("src/main/resources/com/example/maemory/CardSets/"+s+"/").mkdir()) {
			
			Files.move(Path.of(background[0].getUrl().replace("file:/", "").replace("%20", " ")), Path.of("src/main/resources/com/example/maemory/CardSets/" + s + "/background.jpg"), StandardCopyOption.ATOMIC_MOVE);
			
			for (Image image : images) {
				
				Files.move(Path.of(image.getUrl().replace("file:/", "").replace("%20", " ")), Path.of("src/main/resources/com/example/maemory/CardSets/" + s + "/" + i + ".jpg"));
				i++;
				
			}
			
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("could not create directory");
			alert.show();
			throw new FileNotFoundException("could not create directory");
		}
		
		
	}
	
	public static String showCardSetSelectionDialog (int size) {
		
		final boolean[] newSet = {false};
		final String[] path = new String[1];
		final ArrayList<Button> buttons = new ArrayList<>();
		Stage stage = new Stage();
		HBox hBox = new HBox();
		Button add_new_set = new Button("add new Set");
		Button confirm = new Button("Confirm");
		VBox vBox = new VBox(hBox,new HBox(add_new_set,confirm));
		BorderPane pane = new BorderPane();
		pane.setCenter(vBox);
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		vBox.setSpacing(15);
		vBox.setAlignment(Pos.valueOf("CENTER"));
		hBox.setSpacing(15);
		add_new_set.setOnAction((q) -> {
			try {
				convertToCardSetWithDialog(100,100,size);
				newSet[0] = true;
				stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		confirm.setOnAction((a) -> stage.close());
		
		for (File file: Objects.requireNonNull(new File("src/main/resources/com/example/maemory/CardSets/").listFiles())) {
			
			if (Objects.requireNonNull(file.listFiles()).length == 0) {
				if(!file.delete()) {
					continue;
				}
				continue;
			}
			
			ImageView imageView = new ImageView();
			Button select = new Button("select "+file.getName());
			VBox imageBox = new VBox(imageView,select);
			buttons.add(select);
			
			imageBox.setSpacing(5);
			hBox.getChildren().add(imageBox);
			try {
				imageView.setImage(new Image(Objects.requireNonNull(file.listFiles())[0].toURI().toURL().toString(),100,100,false,false));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			select.setOnAction((q) -> {
			
				path[0] = file.getPath();
				select.setTextFill(Color.GREEN);
				buttons.forEach((b) -> {
					
					if (b != select) {
						b.setTextFill(Color.BLACK);
					}
					
				});
				
			});
			
		}
		
		stage.showAndWait();
		
		if (newSet[0]) {
			
			return showCardSetSelectionDialog(size);
		}else {
			return path[0];
		}
		
	
	}
	
}
