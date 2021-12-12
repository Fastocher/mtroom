package NC.mtroom.room.api.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class testcontroller {
    @RequestMapping(value = "/pic")
    public String picture(Model model) throws IOException {
        ClassPathResource roompic = new ClassPathResource("src/main/resources/static/img/room1.png");
        File pic = new File(roompic.getPath());
        model.addAttribute("pic",pic);
        roompic.getURL();
        return "picture";
    }
    @RequestMapping("pic/{picname}")
    @ResponseBody
    public void show(@PathVariable ("picname") String picname, HttpServletResponse response){
        response.setHeader("Content-Disposition","attachment; picname =" +picname);
        try{
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/static/img/room1.png");
            int len;
            byte[] buf = new byte[1024];
            while((len=fileInputStream.read(buf)) >0 ){
                bufferedOutputStream.write(buf,0,len);
            }
            bufferedOutputStream.close();
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
