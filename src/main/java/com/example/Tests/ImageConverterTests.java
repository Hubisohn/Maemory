package com.example.Tests;
import com.example.maemory.*;
import javafx.collections.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class ImageConverterTests {
	
	
	
		@Test
		private void convertImageNegativeSize () {
			
			FXCollections.observableArrayList(new ArrayList<Object>());
			
			assertThrows(IllegalArgumentException.class,() -> CardSetFunctions.convertToCardSetWithoutDialog(-1,-1,null,4));
			
		}
	
		@Test
		private void convertImageNoFiles () {
		
			assertThrows(NullPointerException.class,() -> CardSetFunctions.convertToCardSetWithoutDialog(1,1,null,4));
		
		}


}
