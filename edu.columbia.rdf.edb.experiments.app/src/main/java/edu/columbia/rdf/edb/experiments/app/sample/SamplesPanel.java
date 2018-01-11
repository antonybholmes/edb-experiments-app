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

import java.util.List;

import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.widget.ModernWidget;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.SelectedSamples;

// TODO: Auto-generated Javadoc
/**
 * The Class SamplesPanel.
 */
public abstract class SamplesPanel extends ModernWidget
    implements SelectedSamples {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Adds the selection listener.
   *
   * @param l the l
   */
  public abstract void addSelectionListener(ModernSelectionListener l);

  /**
   * Sets the selected sample.
   *
   * @param name the new selected sample
   */
  public abstract void setSelectedSample(String name);

  /**
   * Sets the selected sample.
   *
   * @param row the new selected sample
   */
  public abstract void setSelectedSample(int row);

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.SelectedSamples#getSelectedSamples()
   */
  public abstract List<Sample> getSelectedSamples();
}
