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
import java.util.Set;

import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.tree.TreeRootNode;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.tree.ModernTree;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.sort.SampleSorter;
import edu.columbia.rdf.edb.ui.sort.SortSamplesByExperiment;

/**
 * Sort samples by experiment.
 * 
 * @author Antony Holmes
 *
 */
public class ViewSamplesByExperiment extends SampleSorter {

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.search.Sorter#arrange(java.util.Collection,
   * org.abh.common.ui.tree.ModernTree, boolean,
   * org.abh.common.ui.search.FilterModel)
   */
  @Override
  public void arrange(Collection<Sample> samples,
      ModernTree<Sample> tree,
      boolean ascending,
      FilterModel filterModel) {

    Map<Experiment, Set<Sample>> experiments = Experiment
        .sortSamplesByExperiment(samples);

    List<Experiment> sortedExperiments = SortSamplesByExperiment
        .sortByTitle(experiments.keySet(), ascending);

    tree.clear();

    TreeRootNode<Sample> root = new TreeRootNode<Sample>();

    TreeNode<Sample> titleNode = new TreeNode<Sample>(getName());

    root.addChild(titleNode);

    for (Experiment experiment : sortedExperiments) {
      TreeNode<Sample> node = new TreeNode<Sample>(experiment.getName());

      List<Sample> sortedSamples = sortByName(experiments.get(experiment),
          ascending);

      for (Sample sample : sortedSamples) {
        node.addChild(new TreeNode<Sample>(sample.getName(), sample));
      }

      node.setIsExpandable(false);

      titleNode.addChild(node);
    }

    tree.setRoot(root);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.sort.SampleSorter#getName()
   */
  public final String getName() {
    return "Experiment";
  }
}
