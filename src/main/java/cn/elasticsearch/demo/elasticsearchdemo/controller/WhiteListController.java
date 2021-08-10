package cn.elasticsearch.demo.elasticsearchdemo.controller;

import cn.elasticsearch.demo.elasticsearchdemo.model.WhiteListDomain;
import cn.elasticsearch.demo.elasticsearchdemo.service.WhiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author qushengxu
 * @date 2021/8/5 10:01
 **/
@RestController
public class WhiteListController {

    @Autowired
    private WhiteService whiteService;
    @GetMapping("/test")
    public String test() {

        return "Success";
    }

    @PostMapping("/profiles")
    public ResponseEntity createProfile(@RequestBody WhiteListDomain whiteListDomain) throws Exception {

        return new ResponseEntity(whiteService.createProfileDocument(whiteListDomain), HttpStatus.CREATED);
    }

//    @PutMapping
//    public ResponseEntity updateProfile(@RequestBody ProfileDocument document) throws Exception {
//
//        return new ResponseEntity(service.updateProfile(document), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ProfileDocument findById(@PathVariable String id) throws Exception {
//
//        return service.findById(id);
//    }
//
//    @GetMapping
//    public List<ProfileDocument> findAll() throws Exception {
//
//        return service.findAll();
//    }
//
//    @GetMapping(value = "/search")
//    public List<ProfileDocument> search(@RequestParam(value = "technology") String technology) throws Exception {
//        return service.searchByTechnology(technology);
//    }
//
//    @GetMapping(value = "/api/v1/profiles/name-search")
//    public List<ProfileDocument> searchByName(@RequestParam(value = "name") String name) throws Exception {
//        return service.findProfileByName(name);
//    }
//
//
//    @DeleteMapping("/{id}")
//    public String deleteProfileDocument(@PathVariable String id) throws Exception {
//
//        return service.deleteProfileDocument(id);
//
//    }
}
