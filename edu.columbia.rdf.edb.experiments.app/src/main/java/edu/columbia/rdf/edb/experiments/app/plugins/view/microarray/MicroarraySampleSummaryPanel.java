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
package edu.columbia.rdf.edb.experiments.app.plugins.view.microarray;

import java.net.MalformedURLException;

import javax.swing.Box;

import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.SampleTag;
import edu.columbia.rdf.edb.experiments.app.plugins.view.GEOUrlLinkButton;
import edu.columbia.rdf.edb.experiments.app.plugins.view.SampleSummaryPanel;
import edu.columbia.rdf.edb.experiments.app.plugins.view.SummaryLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroarraySampleSummaryPanel.
 */
public class MicroarraySampleSummaryPanel extends SampleSummaryPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new microarray sample summary panel.
   *
   * @param sample the sample
   */
  public MicroarraySampleSummaryPanel(Sample sample) {
    super(sample);

    Box box;

    SampleTag field = sample.getTags().getTag(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Array_Platform");

    if (field != null) {
      // add(UI.createVGap(5));

      box = HBox.create();
      box.add(new ModernAutoSizeLabel("Array Platform:", LABEL_SIZE));
      box.add(new SummaryLabel(field.getValue())); // + " (" +
                                                   // sample.getArrayDesign().getProvider()
                                                   // + ")"));
      add(box);
    }

    if (sample.getGEO() != null) {
      // add(UI.createVGap(5));

      box = HBox.create();

      box.add(new ModernAutoSizeLabel("GEO Accession:", LABEL_SIZE));

      try {
        box.add(new GEOUrlLinkButton(sample.getGEO().getGEOSeriesAccession(),
            sample.getGEO().getGEOSeriesAccession()));

        box.add(new ModernAutoSizeLabel(" ("));
        // box.add(UI.createHGap(5));
        box.add(new GEOUrlLinkButton(sample.getGEO().getGEOAccession(),
            sample.getGEO().getGEOAccession()));
        box.add(new ModernAutoSizeLabel(")"));

      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      add(box);
    }
  }
}
