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
package edu.columbia.rdf.edb.experiments.app.sample;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleListEntry.
 */
public class SampleListEntry {

  /** The sample. */
  private Sample sample = null;

  /** The is header. */
  private boolean isHeader;

  /** The title. */
  private String title;

  /**
   * Instantiates a new sample list entry.
   *
   * @param title
   *          the title
   */
  public SampleListEntry(String title) {
    this.title = title;
    this.isHeader = true;
  }

  /**
   * Instantiates a new sample list entry.
   *
   * @param experiment
   *          the experiment
   */
  public SampleListEntry(Experiment experiment) {
    this.title = experiment.getName();
    this.isHeader = true;
  }

  /**
   * Instantiates a new sample list entry.
   *
   * @param sample
   *          the sample
   */
  public SampleListEntry(Sample sample) {
    this.sample = sample;
    this.isHeader = false;
  }

  /**
   * Gets the sample.
   *
   * @return the sample
   */
  public final Sample getSample() {
    return sample;
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  public final String getTitle() {
    return title;
  }

  /**
   * Checks if is header.
   *
   * @return true, if is header
   */
  public final boolean isHeader() {
    return isHeader;
  }
}
