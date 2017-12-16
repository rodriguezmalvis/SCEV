package br.com.scev.negocio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader {

	private String uploadDir = "src\\main\\resources\\static\\uploads\\";
	private String urlFoto = "\\uploads\\";
	
	public String uploadFile(MultipartFile file, String titulo) {
		
		urlFoto = urlFoto + titulo;
		uploadDir = uploadDir + titulo;
		
		try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            urlFoto = "";
        }
		
		return urlFoto;
		
	}

}
