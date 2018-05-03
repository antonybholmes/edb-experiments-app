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
package edu.columbia.rdf.edb.experiments.app.modules.microarray;

import org.jebtk.core.path.RootPath;

import edu.columbia.rdf.edb.ui.microarray.NormalizationDialog;
import edu.columbia.rdf.edb.ui.sort.SortSamplesByTag;

/**
 * The Class SortSamplesByMicroarrayField.
 */
public abstract class SortSamplesByMicroarrayField extends SortSamplesByTag {

  /**
   * Instantiates a new sort samples by microarray field.
   *
   * @param levels the levels
   */
  public SortSamplesByMicroarrayField(Object... levels) {
    super(new RootPath(NormalizationDialog.MICROARRAY_EXPRESSION_DATA, levels));
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.sort.SampleSorter#getType()
   */
  @Override
  public String getType() {
    return "Microarray Properties";
  }
}
