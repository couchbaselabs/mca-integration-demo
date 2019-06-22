/*
 * Copyright 2019 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.spring.mca;

import com.couchbase.client.mc.coordination.Coordinator;
import com.couchbase.client.mc.coordination.Coordinators;
import com.couchbase.client.mc.coordination.IsolatedCoordinator;
import com.couchbase.client.mc.detection.FailureDetector;
import com.couchbase.client.mc.detection.FailureDetectorFactory;
import com.couchbase.client.mc.detection.FailureDetectors;
import com.couchbase.client.mc.detection.NodeHealthFailureDetector;
import com.couchbase.spring.mca.internal.AbstractMultiClusterConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiClusterDatabaseConfiguration extends AbstractMultiClusterConfiguration {

  @Bean
  @Override
  public String bucketName() {
    return "travel-sample";
  }

  @Override
  public String userName() {
    return "Administrator";
  }

  @Override
  public String userPass() {
    return "password";
  }

  @Bean
  @Override
  public Coordinator coordinator() {
    return Coordinators.isolated(new IsolatedCoordinator.Options());
  }

  @Bean
  public FailureDetectorFactory<? extends FailureDetector> failureDetectorFactory() {
    return FailureDetectors.nodeHealth(coordinator(), NodeHealthFailureDetector.options());
  }

}
