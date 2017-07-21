package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.SkateParkDao;
import com.example.skatespots.models.Dao.SkateSpotDao;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.storage.StorageException;
import com.example.skatespots.storage.StorageFileNotFoundException;
import com.example.skatespots.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    @Autowired
    private SkateSpotDao skateSpotDao;

    @Autowired
    private SkateParkDao skateParkDao;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/upload/spot/{spotId}")
    public String listUploadedFileSpot(Model model, @PathVariable int spotId) throws IOException {

        SkateSpot spot = skateSpotDao.findOne(spotId);
        model.addAttribute("spotName", spot.getName());
        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploads/uploadForm";
    }
    @PostMapping("/upload/spot/{spotId}")
    public String handleFileUploadSpot(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes, @PathVariable int spotId, Model model) {

        try {
            storageService.store(file);
            SkateSpot spot = skateSpotDao.findOne(spotId);
            spot.setImgpath(file.getOriginalFilename());
            skateSpotDao.save(spot);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            return "redirect:/spot/{spotId}";
        } catch (StorageException e) {
            SkateSpot spot = skateSpotDao.findOne(spotId);
            model.addAttribute("spotName", spot.getName());
            model.addAttribute("files", storageService
                    .loadAll()
                    .map(path ->
                            MvcUriComponentsBuilder
                                    .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                    .build().toString())
                    .collect(Collectors.toList()));
            return "uploads/uploadForm";
        }
    }
    @GetMapping("/remove/spot/{spotId}")
    public String removeImageSpot(@PathVariable int spotId){

        SkateSpot spot = skateSpotDao.findOne(spotId);
        spot.setImgpath(null);
        skateSpotDao.save(spot);
        return "redirect:/spot/{spotId}";
    }

    @GetMapping("/upload/park/{parkId}")
    public String listUploadedFilePark(Model model, @PathVariable int parkId) throws IOException {

        SkatePark park = skateParkDao.findOne(parkId);
        model.addAttribute("parkName", park.getName());
        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
        return "uploads/uploadForm";
    }
    @PostMapping("/upload/park/{parkId}")
    public String handleFileUploadPark(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes, @PathVariable int parkId, Model model) {
        try {
        storageService.store(file);
        SkatePark park = skateParkDao.findOne(parkId);
        park.setImgpath(file.getOriginalFilename());
        skateParkDao.save(park);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/park/{parkId}";
        } catch (StorageException e) {
            SkatePark park = skateParkDao.findOne(parkId);
            model.addAttribute("parkName", park.getName());
            model.addAttribute("files", storageService
                    .loadAll()
                    .map(path ->
                            MvcUriComponentsBuilder
                                    .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                    .build().toString())
                    .collect(Collectors.toList()));

            return "uploads/uploadForm";
        }

    }
    @GetMapping("/remove/park/{parkId}")
    public String removeImagePark(@PathVariable int parkId){

        SkatePark park = skateParkDao.findOne(parkId);
        park.setImgpath(null);
        skateParkDao.save(park);
        return "redirect:/park/{parkId}";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
