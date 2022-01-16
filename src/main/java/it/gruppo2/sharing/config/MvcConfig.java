package it.gruppo2.sharing.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.gruppo2.sharing.service.VeicoloServiceImpl;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
 
	// rende visibile dall'esterno la cartella delle photografie, se il percorso è corretto
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(VeicoloServiceImpl.percorsoImg, registry);
    }
     
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
         
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
         
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}
