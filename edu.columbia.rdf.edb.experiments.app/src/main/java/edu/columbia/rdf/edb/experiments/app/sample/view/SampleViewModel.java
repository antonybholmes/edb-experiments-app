/**
 * Copyright 2017 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.columbia.rdf.edb.experiments.app.sample.view;

import org.jebtk.modern.search.SortModel;

import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * Sort model for determining how the folder view appears.
 * 
 * @author Antony Holmes
 *
 */
public class SampleViewModel extends SortModel<Sample> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  // private static final SampleViewService INSTANCE = new SampleViewService();

  // public static final SampleViewService getInstance() {
  // return INSTANCE;
  // }

  /**
   * Instantiates a new sample view model.
   */
  public SampleViewModel() {
    add(new ViewSamplesByExperiment());
    add(new ViewSamplesByOrganism());
    add(new ViewSamplesByPerson());
    add(new ViewSamplesByExpressionType());
    add(new ViewSamplesByGeoSeries());
    add(new ViewSamplesByGeoPlatform());

    setDefault("Expression Type");
  }
}
