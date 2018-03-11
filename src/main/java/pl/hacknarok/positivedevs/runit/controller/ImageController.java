package pl.hacknarok.positivedevs.runit.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.hacknarok.positivedevs.runit.MotorolaJSONFactory;
import pl.hacknarok.positivedevs.runit.UtilBase64Image;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.Image;
import pl.hacknarok.positivedevs.runit.entity.Logo;
import pl.hacknarok.positivedevs.runit.repository.EventRepository;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("image")
public class ImageController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public void add(@RequestBody Image image) throws JSONException { //TODO INNE BODY!
        System.out.println("/POST request with " + image.toString());
        // save Image to C:\\server folder
        String path = "C:\\Users\\Krzysiek\\Documents\\Studia\\hacknarock2018\\Hacknarok-2018\\target\\classes\\images\\" + image.getName();
        UtilBase64Image.decoder(image.getData(), path);

        final String uri = "https://hacknarock.release.commandcentral.com";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Basic MFlrY2htcllOM2NxMXpM");

        String jsonToSend = MotorolaJSONFactory.createImageJSON(image.getName(),image.getLatitude(),image.getLongitude(),"http://53eabf09.ngrok.io/image/get/" + image.getName());
        HttpEntity<String> entity = new HttpEntity<>(jsonToSend, headers);

        restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
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