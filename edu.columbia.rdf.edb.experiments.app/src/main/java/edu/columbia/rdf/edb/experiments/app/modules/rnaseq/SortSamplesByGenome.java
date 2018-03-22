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

// TODO: Auto-generated Javadoc
/**
 * The Class SortSamplesByGenome.
 */
public class SortSamplesByGenome extends SortSamplesByRnaSeqField {

  /**
   * Instantiates a new sort samples by genome.
   */
  public SortSamplesByGenome() {
    super("/RNA-seq/Sample/Genome");
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.sort.SampleSorter#getName()
   */
  @Override
  public final String getName() {
    return "Genome";
  }
}
