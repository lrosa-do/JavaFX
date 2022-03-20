
 
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import utils.Tools;

/**
 *
 * @author djoker
 */
public class CamsView extends  Application {

 
    private Integer count=0;
    private Integer MaxCams=80;

public static void main(String[] args) throws IOException 
{
 
    
    launch(args);
}

@Override
public void start(Stage stage) {

    List<ImageDataObjs> imageURLs = new  ArrayList<>();
    
String url = "https://www.cam4.com/directoryCounts?directoryJson=true&online=true&url=true&gender=female&broadcastType=female_group&broadcastType=solo&broadcastType=male_female_group&page=1&orderBy=VIDEO_QUALITY&resultsPerPage=20";

String url_br="https://www.cam4.com/directoryCams?directoryJson=true&online=true&url=true&country=br&gender=female&broadcastType=female_group&broadcastType=solo&broadcastType=male_female_group&page=1&orderBy=MOST_VIEWERS&resultsPerPage=60";
    
String url_pt="https://www.cam4.com/directoryCams?directoryJson=true&online=true&url=true&country=pt&gender=female&broadcastType=female_group&broadcastType=solo&broadcastType=male_female_group&page=1&orderBy=MOST_VIEWERS&resultsPerPage=60";

getCam4(url,imageURLs,1);

    
    ScrollPane sp = new ScrollPane();
    

    GridPane root = new GridPane();
    sp.setContent(root);
    Scene theScene = new Scene(sp,600, 450);
    stage.setTitle("Some scene");
    stage.setScene(theScene);

    root.setPadding(new Insets(10,10,10,10));
    root.setHgap(10);
    root.setVgap(10);

    int cols=2, colCnt = 0, rowCnt = 0;
    for (int i=0; i<imageURLs.size(); i++) 
    {
        //root.add(imageURLs.get(i).imageView, colCnt, rowCnt);
       // root.add(imageURLs.get(i).label, colCnt, rowCnt);
        //root.add(imageURLs.get(i).button, colCnt, rowCnt);
        root.add(imageURLs.get(i).box, colCnt, rowCnt);
     
      
        colCnt++;
        if (colCnt>cols) {
            rowCnt++;
            colCnt=0;
        }
    }


    stage.show();
}

class Performer{
    public String name;
    public String url;
    public String video;
    public String img;
    public String data;
    public Performer(String name,String url,String video, String img,String data)
    {
        this.name=name;
        this.url=url;
        this.img=img;
        this.data=data;
        this.video=video;
        
    }
    
}

class ImageDataObjs {
    final ImageView imageView;
    final Label label;
    final Button button;
    final VBox box;
    
    String imgURL, name,video;

    public ImageDataObjs(String imgURL, String Video, String name) {
        this.imgURL = imgURL;
        this.name = name;
        this.video=Video;
        //Path imagePath = Paths.get(imgURL);
        //File imageFile = imagePath.toFile();
        
     //   System.out.println(" image "+imgURL);
        Image image = new Image(imgURL);
        //Image image = new Image("https://i.stack.imgur.com/MPNMM.png");
        //saveToFile(image,name+".jpg");
       // Image image = new Image(imgURL);
       
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
        final String playvideo=Video;
        
        this.label = new Label(name);
        this.button = new Button("Click Me");
        this.button.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
              
              try {
		Tools.copyToClipboardText(playvideo);                    
		Runtime.getRuntime().exec("mpv "+playvideo);
		
                 } catch (IOException ex) {
                    Logger.getLogger(CamsView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
           //Runtime.getRuntime().exec("mpv https://cam4-hls.xcdnpro.com/259/cam4-origin-live/NalgonaSex-259-795fe0d7-c2ce-4e8c-80cc-7dfb85b04900_aac/playlist.m3u8");
        
        this.box = new VBox();
        box.setStyle("-fx-background-color: #336699;");
        box.setPadding(new Insets(4, 8, 4, 8));
        box.setSpacing(5);
        
        
        HBox vbox = new HBox();
        vbox.getChildren().addAll(button,label);
        vbox.setSpacing(10);
        
        box.getChildren().addAll(imageView, vbox);
        //box.getChildren().addAll( vbox);
        
    }
}

 public static void saveToFile(Image img,String name) 
 {
 
int width = (int) img.getWidth();
int height = (int) img.getHeight();
System.out.println(width);
System.out.println(height);
PixelReader reader = img.getPixelReader();



byte[] buffer = new byte[width * height * 4];
WritablePixelFormat<ByteBuffer> format = PixelFormat.getByteBgraInstance();
reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
try {
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/media/djoker/code/linux/python/projects/JavaFX/projects/CamsView/img/"+name));
    
    for(int count = 0; count < buffer.length; count += 4) 
    {
        out.write(buffer[count + 2]);
        out.write(buffer[count + 1]);
        out.write(buffer[count]);
        out.write(buffer[count + 3]);
    }
    out.flush();
    out.close();
    
} catch(IOException e) {
    e.printStackTrace();
}

  }

 public static String sendGet(String url) throws Exception {

		URL obj = new URL(url);
                
                
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        	con.setRequestMethod("GET");
         	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 9; MAX 2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Mobile Safari/537.36'");
                con.setRequestProperty("Accept", "*/*");
                con.setRequestProperty("Connection", "keep-alive");
                
                con.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                con.addRequestProperty("Referer", "google.com");
                con.setConnectTimeout(60);
             
                
        	int responseCode = con.getResponseCode();
                boolean redirect = false;

	//  301 is redirect
	
	if (responseCode != HttpURLConnection.HTTP_OK) {
		if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
			|| responseCode == HttpURLConnection.HTTP_MOVED_PERM
				|| responseCode == HttpURLConnection.HTTP_SEE_OTHER)
		redirect = true;
	}
        

	//System.out.println("Response Code ... " + responseCode);
        
        if (redirect) 
        {
        	String newUrl = con.getHeaderField("Location");
		con = (HttpURLConnection) new URL(newUrl).openConnection();
		System.out.println("Redirect to URL : " + newUrl);
                responseCode = con.getResponseCode();
               // System.out.println("Response Code ... " + responseCode);

	}

        
        
                
		
	
        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer(); 
               while((line = in.readLine()) != null) 
               {
                 response.append(line);
                 response.append('\r');
               }
         
		in.close();
                con.disconnect();

		 return response.toString();

	}


public void getCam4(String url,List<ImageDataObjs> lista,Integer page)
{
String data="";
         try {
             data = sendGet(url);
              //     Writer writer = null;
              //   writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/djoker/NetBeansProjects/JavaFXApplication1/cam4.html"), "utf-8"));
              //   writer.write(data);
              //   writer.close();
         } catch (Exception ex) 
         {
             System.out.println("GET " + ex);
         }
     
    //System.out.println(data);

  JSONParser parser = new JSONParser();

		
  //    try{
//JSONObject obj = (JSONObject) = parser.parse(data);

//JSONArray users =  (JSONArray) obj.get("users");
 JSONObject jsonObject=(JSONObject)JSONValue.parse(data);
 JSONArray jsonArray=(JSONArray)jsonObject.get("users");

 for (  Object o : jsonArray) 
{
    JSONObject userObj=(JSONObject)o;
    //System.out.println(userObj.toString());
String name    =      userObj.get("username").toString();
String video    =      userObj.get("hlsPreviewUrl").toString();
String pais    =      userObj.get("countryCode").toString();
String viewers    =      userObj.get("viewers").toString();
String image    =      userObj.get("snapshotImageLink").toString();
String age    =      userObj.get("age").toString();
//System.out.println(name);
//System.out.println(video);
//System.out.println(viewers);
//System.out.println(image);

 lista.add(new ImageDataObjs(image,video, name+"\n"+pais+"\n"+age+" "+viewers));


}

 
}

public void getCamChartubate(String url,List<ImageDataObjs> lista,Integer page)
{
        String exp="<li class=\"room_list_room\">.*?<a href=\"([^\"]+)\">.*?<img src=\"([^\"]+)\".*?<span class=\"age genderf\">([^\"]+)</span>.*?<li class=\"location\" style=\"display: none;\">([^\"]+)</li>.*?<li class=\"cams\">([^\"]+) viewers</li>";
        String data = null;
         try {
             data = sendGetSimples(url);
               //  Writer writer = null;
               //  writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/djoker/NetBeansProjects/JavaFXApplication1/chartubate.html"), "utf-8"));
               //  writer.write(data);
               //  writer.close();
         } catch (Exception ex) 
         {
             System.out.println("erro on get " + ex);
             return ;
         }
     

     Matcher matcher= Pattern.compile(exp).matcher(data);
     while (matcher.find())
      {
        if (count>=MaxCams) return;
       
            String videopage=matcher.group(1);
            String img=matcher.group(2);
            String location=Tools.cleantext(matcher.group(4));
            String viewer=Tools.cleantext(matcher.group(5));
            
            String age=Tools.cleantext(matcher.group(3));
            String name=Tools.cleantext(videopage);
            
            videopage = "https://chaturbate.com" + videopage;
        
//         System.out.println(name);
      

         
        
        //lista.add(new Performer(name,videourl,videourl,image,pais+":"+tempo+":"+viewers));
        lista.add(new ImageDataObjs(img, videopage,name+"\n"+location+"\n"+age+":"+viewer));
      
       

        
      
       count++;
      }
     
     System.out.println("Total:"+count);
  
     exp="<li><a href=\"([^\"]+)\" class=\"next endless_page_link\">next</a></li>";
     //exp="<a href=\"([^\"]+)\" class=\"next'";
     try {
         Matcher matcher2= Pattern.compile(exp).matcher(data);  
         while (matcher2.find())
      {
         Integer npage=page+1;
         url = "https://chaturbate.com"+matcher2.group(1);
         getCamChartubate(url,lista,npage);     
      }
         
     
         } catch (Exception ex) {
             System.out.println("nova pagina falhou " + ex);
         }
      
}


    public static  String sendGetSimples(String url) throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        con.setRequestProperty("Accept", "*/*");
        int responseCode = con.getResponseCode();
           System.out.println("Sending 'GET' request to URL : " + url);
           System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = in.readLine()) != null)
        {
            response.append(line);
            response.append('\r');
        }

        in.close();
        con.disconnect();

        return response.toString();

    }

}
