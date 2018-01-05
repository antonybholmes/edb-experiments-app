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
package edu.columbia.rdf.edb.experiments.app.search;

import java.util.Collection;

import org.jebtk.modern.widget.ModernWidget;

import edu.columbia.rdf.edb.Experiment;

// TODO: Auto-generated Javadoc
/**
 * The Class ExperimentsTab.
 */
public abstract class ExperimentsTab extends ModernWidget {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Sets the experiments.
   *
   * @param experiments
   *          the new experiments
   */
  public abstract void setExperiments(Collection<Experiment> experiments);
}
