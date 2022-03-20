/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;


import java.io.InputStream;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author djoker
 */
public final class Tools {
    
      public static String newline = System.getProperty("line.separator");
        
   
public static void copyToClipboardText(String s) {

		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();

		content.putString(s);
		clipboard.setContent(content);

	}

	public static void copyToClipboardImage(Label lbl) {

		WritableImage snapshot = lbl.snapshot(new SnapshotParameters(), null);
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();

		content.putImage(snapshot);
		clipboard.setContent(content);

	}

	public static void copyToClipboardImageFromFile(String path) {

		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();

		content.putImage(Tools.getImage(path));
		clipboard.setContent(content);

	}

	public static Image getImage(String path) {

		InputStream is = Tools.class.getResourceAsStream(path);
		return new Image(is);
	}
	
	
	public static ImageView setIcon(String path) {

		InputStream is = Tools.class.getResourceAsStream(path);
		ImageView iv = new ImageView(new Image(is));

		iv.setFitWidth(100);
		iv.setFitHeight(100);
		return iv;
	}

   
    // HTTP GET request
	public static String sendGetUserAgent(String url,String agent) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                con.setRequestMethod("GET");
         	con.setRequestProperty("User-Agent", agent);
            //    con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            //    con.addRequestProperty("Referer", "google.com");
             //   con.setConnectTimeout(60);

      
            
        	int responseCode = con.getResponseCode();
                boolean redirect = false;

	//  301 is redirect
	
	if (responseCode != HttpURLConnection.HTTP_OK) {
		if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
			|| responseCode == HttpURLConnection.HTTP_MOVED_PERM
				|| responseCode == HttpURLConnection.HTTP_SEE_OTHER)
		redirect = true;
	}
        

	  //  System.out.println("Response Code ... " + responseCode);
            
        if (redirect) 
        {
        
         
            
        	String newUrl = con.getHeaderField("Location");
                System.out.println("Redirect to URL : " + newUrl);
        	con = (HttpURLConnection) new URL(newUrl).openConnection();
                con.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
  
             
		/*
            System.out.println("headers redirect");
            System.out.println(con.getHeaderField(0));
            System.out.println(con.getHeaderField(1));
            System.out.println(con.getHeaderField(2));
            System.out.println(con.getHeaderField(3));
            System.out.println(con.getHeaderField(4));
            System.out.println(con.getHeaderField(5));
            System.out.println(con.getHeaderField(6));
            System.out.println(con.getHeaderField(7));
            System.out.println(con.getHeaderField(8));
            System.out.println(con.getHeaderField(9));
            System.out.println(con.getHeaderField(10));
            System.out.println(con.getHeaderField(11));
       
            System.out.println(".");
            System.out.println("..");
            System.out.println("...");
        */
            
                
                responseCode = con.getResponseCode();
             //   System.out.println("Response Code ... " + responseCode);

	}

        
        
                
		
	
        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer(); 
               while((line = in.readLine()) != null) 
               {
                 response.append(line);
                 response.append("\n");
               }
         
		in.close();
                con.disconnect();

		 return response.toString();

	}
            public static String cleantext(String text)    
    {
     text = text.replace("&amp;", "&");
    text = text.replace("&#8211;", "-");
    text = text.replace("&ndash;", "-");
    text = text.replace("&#038;", "&");
    text = text.replace("&#8217;", "\"");
    text = text.replace("&#8216;", "\"");
    text = text.replace("&#8230;", "...");
    text = text.replace("&quot;", "\"");
    text = text.replace("&#039;", "`");
    text = text.replace("&rsquo;", "\"");
    text = text.replace("/", "");
    text = text.replace("/", "");
    text = text.replace("&nbsp", "");
        return text;
    }
      public static String readFile(String filename) {
            File f = new File(filename);
            try {
                byte[] bytes = Files.readAllBytes(f.toPath());
                return new String(bytes,"UTF-8");
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            return "";
    }

   public static void saveFile(String fname,String data)
   {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter( fname));
        } catch (IOException ex) {
            System.out.println("\n"+ ex);
        }
        try {
            writer.write( data);
        } catch (IOException ex) {
            System.out.println("\n"+ex);
        }
      try
      {
          if ( writer != null)
              writer.close( );
      }
      catch ( IOException e)
      {
      }
   }
}
