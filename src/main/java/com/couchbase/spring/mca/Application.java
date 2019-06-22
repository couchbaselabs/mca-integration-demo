/*
 * Copyright (c) 2019 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.spring.mca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@Controller
public class Application {

  @Autowired
  private CouchbaseTemplate template;

  @Autowired
  private AirportRepository repository;

  @GetMapping("/airports/{id}")
  public ResponseEntity<Airport> read(@PathVariable String id) {
    Airport airport =  template.findById("airport_" + id, Airport.class);
    return new ResponseEntity<>(airport, HttpStatus.OK);
  }

  @GetMapping("/airports")
  public ResponseEntity<Iterable<Airport>> readAll() {
    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
