package com.example.maemory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Spielkarte {
    final private int index;
    final private Image vorderseite;
    final private int vorderseiteID;


    public Spielkarte(int index, String imagePath,int id) {
        this.vorderseiteID = id;
        this.index = index;
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.vorderseite = image;
    }

    public Image getVorderseite() {
        return vorderseite;
    }

    public int getIndex() {
        return index;
    }

    public int getVorderseiteID() {
        return vorderseiteID;
    }
}
