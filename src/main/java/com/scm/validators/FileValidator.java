package com.scm.validators;

import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>{

    private static final long MAX_FILE_SIZE=1024*1024*5; //5mb

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
         if(file==null || file.isEmpty()){
            // context.disableDefaultConstraintViolation(); //disabling default msg
            // context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();

            // return false;
            return true;
         }

         //file size
         if(file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation(); //disabling default msg
            context.buildConstraintViolationWithTemplate("File should be less than 5 mb").addConstraintViolation();
            return false;
         }

         //resulution
        //   try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     //can check everything
        //     if(bufferedImage.getWidth()>1000){

        //     }
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
         return true;
    }

}
