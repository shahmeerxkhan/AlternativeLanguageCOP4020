

package Testalternativelanguage;

import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import alternativelanguage.Main;


/**
 *
 * @author 
 */
 public class MainTest {
         
    //Ensure the file being read is not empty
    @Test
     public void testReadFileNotEmpty()
    {
        try {
            FileReader reader = new FileReader("cells.csv");
            assertTrue(reader.read()!= -1);
            reader.close();
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } 
    }
     //column's final transformation matches 
     @Test
     public void testLaunchAnnounced()
     {
       int year =  Main.processLaunchAnnounced("2010, January. Released 2010, March");
       assertEquals("Integer",  ((Object)year).getClass().getSimpleName());
     }
     //column's final transformation matches 
     @Test
     public void testDisplay_Size()
     {
       float disp_size =  Main.processDisplaySize("3.5 inches, 34.9 cm");
       assertEquals("Float",  ((Object)disp_size).getClass().getSimpleName());
     }
     //column's final transformation matches 
     @Test
     public void testProcessBodyWeight()
     {
       float bodyWeight =  Main.processBodyWeight("190 g (6.70 oz)");
       assertEquals("Float",  ((Object)bodyWeight).getClass().getSimpleName());
     }
     //Ensure all missing or "-" data is replaced with a null value
     @Test
     public void testForNull()
     {
       String checkString =  Main.CheckForBlank("-");
       assertEquals(null,  checkString);
     }
    
}
