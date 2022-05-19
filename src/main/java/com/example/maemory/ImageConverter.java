package com.example.maemory;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import org.jetbrains.annotations.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ImageConverter {
	
	public static ArrayList<Spielkarte> convertWithDialog( Integer width, Integer height) throws Exception{
		
		DirectoryChooser chooser = new DirectoryChooser();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Important!");
		alert.setContentText("The directory you select must contain an amount of image files equal to a power of 2 + 1.\nThe image to be used as the back of every card must contain \"back\" or \"background\" in its filename, the filenames of the other images do not matter.");
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
			
			return convertWithoutDialog(width, height, file.listFiles());
			
		}
		
	}
	
	public static ArrayList<Spielkarte> convertWithoutDialog (final Integer width, final Integer height, @NotNull File[] files) throws Exception {
		
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
			
		}else if (counter[0] != Stream.of(Objects.requireNonNull(files)).count()) {
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Not all files in this folder are image files, reducing total number of cards");
			alert.showAndWait();
			
			images.removeIf(Image::isError);
			
			
				for (int i = 0; Math.pow(2,i) < 32; i++) {
					
					if (images.size() < Math.pow(2,i)) {
						
						if (images.size() == Math.pow(2,i - 1)) {
							break;
						}
						
						int amtToRemove = (int) (images.size() - Math.pow(2, i - 1));
						
						for (int j = 0; j < amtToRemove; j++) {
							
							images.remove(images.size() - 1);
							
						}
					}else if (images.size() == Math.pow(2,i)) {
						break;
					}
					
				}
			
		} else if (background[0] == null) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			alert.show();
			throw new IllegalArgumentException("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
			
		}
		
		images.forEach((i) -> spielkartes.add(new Spielkarte(i,background[0])));
		
		return spielkartes;
		
		
	}
	
}
