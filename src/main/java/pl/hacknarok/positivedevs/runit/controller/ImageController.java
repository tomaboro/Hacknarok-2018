package pl.hacknarok.positivedevs.runit.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import pl.hacknarok.positivedevs.runit.UtilBase64Image;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.Image;
import pl.hacknarok.positivedevs.runit.entity.Logo;
import pl.hacknarok.positivedevs.runit.repository.EventRepository;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("image")
public class ImageController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public void add(@RequestBody Image image) {
        System.out.println("/POST request with " + image.toString());
        // save Image to C:\\server folder
        String path = "C:\\Users\\Krzysiek\\Documents\\Studia\\hacknarock2018\\Hacknarok-2018\\target\\classes\\images\\" + image.getName();
        UtilBase64Image.decoder(image.getData(), path);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public  byte[] get(@RequestParam("name") String name) throws IOException {
        System.out.println(name);

        InputStream in = getClass().getResourceAsStream("/images/"+name);
       // System.out.println(System.getProperty(in.));
        //return IOUtils;
        //org.apache.commons.logging.
        return IOUtils.toByteArray(in);
/*
        System.out.println(String.format("/GET info: imageName = %s", name));
        String imagePath = "C:\\RunIT\\" + name;
        String imageBase64 = UtilBase64Image.encoder(imagePath);

        if(imageBase64 != null){
            Image image = new Image(name, imageBase64);
            //return image;
            //InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
            //return IOUtils.toByteArray(in);
        }
        return null;*/
    }
}