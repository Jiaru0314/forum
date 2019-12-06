package com.jit.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @program: blog
 * @description: 图片控制器
 * @author: XZQ
 * @create: 2019-10-29 21:04
 **/
@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/{filename}.{suffix}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename, @PathVariable String suffix) {
        File file = new File("/image/" + filename + '.' + suffix);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(file));
    }
}