package com.example.maemory;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class CardSetFunctions {
	
	public static ArrayList<Spielkarte> convertToCardSetWithDialog(Integer width, Integer height, Integer size) throws Exception{
		
		DirectoryChooser chooser = new DirectoryChooser();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Important!");
		alert.setContentText("The directory you select must contain an amount of image files equal to "+ (int) Math.pow(size, 2)+"+ 1.\nThe image to be used as the back of every card must contain \"back\" or \"background\" in its filename, the filenames of the other images do not matter.");
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
			
			return convertToCardSetWithoutDialog(width, height, file.listFiles(), size);
			
		}
		
	}
	
	public static ArrayList<Spielkarte> convertToCardSetWithoutDialog(final Integer width, final Integer height, File[] files, Integer size) throws Exception {
		
		if ((width != null && height != null) && (width <= 0 || height <= 0)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("width and height must be greater than 0");
			alert.show();
			throw new IllegalStateException("width and height must be greater than 0");
		}

		ArrayList<Spielkarte> spielkartes = new ArrayList<>();
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
					
					if (f.getName().contains("background") || f.getName().contains("back")) {
						
						background[0] = image/*new Image(new FileInputStream(f))*/;

						
					} else {
						
						images.add(image/*new Image(new FileInputStream(f))*/);
						
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
		
		images.removeIf(Image::isError);
		
		if (ex[0] != null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(ex[0].getMessage());
			alert.show();
			throw ex[0];
			
		}else if (counter[0] == 0) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No Files provided are images");
			alert.show();
			throw new IllegalStateException("No Files provided are images");
			
		}else if (Math.pow(size, 2) != images.size()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Not Enough images provided");
			alert.show();
			throw new IllegalStateException("Not Enough images provided");
			
		} else if (background[0] == null) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			alert.show();
			throw new IllegalArgumentException("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			
		}
		
		images.forEach((i) -> spielkartes.add(new Spielkarte(i,background[0])));
		
		return spielkartes;
		
		
	}
	
	public void showCarSetSelectionDialog () {
	
	
		
	
	
	}
	
}