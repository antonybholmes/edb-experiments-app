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

// TODO: Auto-generated Javadoc
/**
 * The Class SortSamplesBySourceDiseaseStatus.
 */
public class SortSamplesBySourceDiseaseStatus extends SortSamplesByMicroarrayField {
	
	/**
	 * Instantiates a new sort samples by source disease status.
	 */
	public SortSamplesBySourceDiseaseStatus() {
		super("Sample",
				"Source", 
				"Characteristic",
				"Disease_Status");
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.edb.ui.sort.SampleSorter#getName()
	 */
	public final String getName() {
		return "Source Disease Status";
	}
}
