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
		
		if (Stream.of(Objects.requireNonNull(files)).count() == 17 || Stream.of(Objects.requireNonNull(files)).count() > 17) {
			
			Stream.of(Objects.requireNonNull(files)).forEach((f) -> {
				
				try {
					
					if (!f.getName().contains("background") && background[0] != null) {
						
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
			
			images.forEach((i) -> spielkartes.add(new Spielkarte(i,background[0])));
			
			}else {
				
				throw new IllegalArgumentException("there must be at least 17 pictures");
				
			}
		
		return spielkartes;
		
		
	}
	
}
