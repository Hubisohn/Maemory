package com.example.spielkartensystem;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import org.jetbrains.annotations.*;
import java.io.*;
import java.nio.file.spi.*;
import java.util.*;
import java.util.stream.*;

public class ImageConverter {
	
	public static ArrayList<Spielkarte> convertWithDialog(int width, int height) throws Exception{
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("choose directory with images");
		File file = chooser.showDialog(null);
		
		if (!file.isDirectory()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("you must select a directory");
			alert.show();
			throw new IllegalArgumentException("you must select a directory");
			
		}else {
			
			return convertWithoutDialog(width,height,file.listFiles());
			
		}
		
	}
	
	public static ArrayList<Spielkarte> convertWithoutDialog (@NotNull Integer width,@NotNull Integer height, @NotNull File... files) throws Exception {
		
		if (width <= 0 || height <= 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("width and height must be greater than 0");
			alert.show();
			throw new IllegalArgumentException("width and height must be greater than 0");
		}
		
		ArrayList<Spielkarte> spielkartes = new ArrayList<>();
		ArrayList<Image> images = new ArrayList<>();
		final Image[] background = {null};
		final Exception[] ex = {null};
		boolean valid = false;
		
		for (int i = 0; Math.pow(2,i) < 32 ; i++) {
			
			if (Stream.of(Objects.requireNonNull(files)).count() == Math.pow(2,i)+1) {
				valid = true;
			}
			
		}
		
		if (valid) {
			
			Stream.of(Objects.requireNonNull(files)).forEach((f) -> {
				
				try {
					
					Image image = new Image(f.getPath(),width,height,false,false);
					
					if (image.isError()) {
						throw new LoadException("File is not of type image");
					}
					
					if ((!f.getName().contains("background") || !f.getName().contains("back")) && background[0] != null) {
						
						images.add(image/*new Image(new FileInputStream(f))*/);
						
					}else {
						
						background[0] = image/*new Image(new FileInputStream(f))*/;
						
					}
					
				} catch (Exception e) {
					ex[0] = e;
				}
				
			});
			
			if (ex[0] != null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText(ex[0].getMessage());
				alert.show();
				throw ex[0];
			}
			
			if (background[0] == null) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
				alert.show();
				throw new IllegalStateException("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
				
			}
			
			images.forEach((i) -> spielkartes.add(new Spielkarte(i,background[0])));
			
			}else {
			
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("the amount of pictures has to be equal to a power of 2 + 1");
				alert.show();
				throw new IllegalStateException("the amount of pictures has to be equal to a power of 2 + 1");
				
			}
		
		return spielkartes;
		
		
	}
	
}
