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
package edu.columbia.rdf.edb.experiments.app.modules.rnaseq;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.modules.SampleSummaryPanel;
import edu.columbia.rdf.edb.ui.SampleDataPanel;

// TODO: Auto-generated Javadoc
/**
 * Displays all of the information pertaining to a particular sample.
 * 
 * @author Antony Holmes
 *
 */
public class RnaSeqSampleDataPanel extends SampleDataPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new rna seq sample data panel.
   *
   * @param sample the sample
   * @param view the view
   */
  public RnaSeqSampleDataPanel(Sample sample, DataView view) {
    super(sample, view);

    SampleSummaryPanel titlePanel = new SampleSummaryPanel(sample);

    setHeader(titlePanel);
  }
}
