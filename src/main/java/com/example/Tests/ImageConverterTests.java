package com.example.Tests;
import com.example.maemory.*;
import javafx.scene.control.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class ImageConverterTests {

		@Test
		private void convertImageNegativeSize () throws Exception {
			
			assertThrows(IllegalArgumentException.class,() -> ImageConverter.convertWithoutDialog(-1,-1,null));
			
		}
	
		@Test
		private void convertImageNoFiles () throws Exception {
		
			assertThrows(NullPointerException.class,() -> ImageConverter.convertWithoutDialog(1,1,null));
		
		}


}
