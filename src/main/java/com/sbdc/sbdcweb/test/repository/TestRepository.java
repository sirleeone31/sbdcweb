package com.sbdc.sbdcweb.test.repository;

import com.sbdc.sbdcweb.test.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface TestRepository extends JpaRepository<Test,Long> {
}
