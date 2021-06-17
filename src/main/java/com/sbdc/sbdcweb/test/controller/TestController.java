package com.sbdc.sbdcweb.test.controller;

import com.sbdc.sbdcweb.test.domain.Test;
import com.sbdc.sbdcweb.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class TestController {

    private final TestRepository testRepository;

    @Autowired
    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("/test")
    public Test create(@RequestBody Test test) {
        return testRepository.save(test);
    }

    @GetMapping("/tests")
    public List<Test> getAll() {
        return testRepository.findAll();
    }
}
