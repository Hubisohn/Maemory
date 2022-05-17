package com.example.spielkartensystem;

import javafx.scene.image.*;
import javafx.stage.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ImageConverter {
	
	public static ArrayList<Spielkarte> convertWithDialog(int width, int height) throws Exception{
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("choose directory with images");
		File file = chooser.showDialog(null);
		
		if (!file.isDirectory()) {
			
			throw new IllegalArgumentException("you must select a directory");
			
		}else {
			
			return convertWithoutDialog(width,height,file.listFiles());
			
		}
		
	}
	
	public static ArrayList<Spielkarte> convertWithoutDialog (@NotNull Integer width,@NotNull Integer height, @NotNull File... files) throws Exception {
		
		if (width <= 0 || height <= 0) {
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
					
					if ((!f.getName().contains("background") || !f.getName().contains("back")) && background[0] != null) {
						
						images.add(new Image(f.getPath(),width,height,false,false)/*new Image(new FileInputStream(f))*/);
						
					}else {
						
						background[0] = new Image(f.getPath(),width,height,false,false)/*new Image(new FileInputStream(f))*/;
						
					}
					
				} catch (Exception e) {
						ex[0] = e;
				}
				
			});
			
			if (ex[0] != null) {
				throw ex[0];
			}
			
			if (background[0] == null) {
				
				throw new IllegalStateException("there has to be at least one picture to be used as a back for the cards, this picture must contain \"back\" or \"background\" in its filename");
				
			}
			
			images.forEach((i) -> spielkartes.add(new Spielkarte(i,background[0])));
			
			}else {
				
				throw new IllegalArgumentException("the amount of pictures has to be equal to a power of 2 + 1");
				
			}
		
		return spielkartes;
		
		
	}
	
}
