package com.scm.services.imple;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helpers.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImple implements ImageService{

  
    private Cloudinary cloudinary;

    public ImageServiceImple(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {
        // TODO Auto-generated method stub
        //code to upload image on cloud
        
        //GENERATING RANDOM FILE NAME
        // String filename=UUID.randomUUID().toString();
         try {
            //create array of byte
            byte[] data=new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data); //storing data in array
            //uploading
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        }
        //and return url
       
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        // TODO Auto-generated method stub
        return cloudinary.url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
    }

    

}
