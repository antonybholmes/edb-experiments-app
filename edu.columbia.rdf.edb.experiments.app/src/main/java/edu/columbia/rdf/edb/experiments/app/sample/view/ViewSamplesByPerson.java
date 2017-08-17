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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jebtk.core.collections.ArrayListMultiMap;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.tree.ModernTree;

import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.SampleView;

// TODO: Auto-generated Javadoc
/**
 * Sort samples by Array Design.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class ViewSamplesByPerson extends SampleView {
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.search.Sorter#arrange(java.util.Collection, org.abh.common.ui.tree.ModernTree, boolean, org.abh.common.ui.search.FilterModel)
	 */
	public void arrange(Collection<Sample> samples, 
			ModernTree<Sample> tree, 
			boolean ascending,
			FilterModel filterModel) {
		Map<String, List<Sample>> map = ArrayListMultiMap.create();

		for (Sample sample : samples) {
			for (Person person : sample.getPersons()) {
				String name = person.getName();
			
				if (map.containsKey(name)) {
					map.get(name).add(sample);
				}
			}
		}
		
		arrange(map, ascending, tree);
	}
	


	/* (non-Javadoc)
	 * @see edu.columbia.rdf.edb.ui.sort.SampleSorter#getName()
	 */
	public final String getName() {
		return "Person";
	}
}
