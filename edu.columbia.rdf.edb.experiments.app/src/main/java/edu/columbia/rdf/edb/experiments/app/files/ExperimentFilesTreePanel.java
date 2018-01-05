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
package edu.columbia.rdf.edb.experiments.app.files;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.tree.TreeRootNode;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.panel.ModernGradientPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.tree.ModernTree;
import org.jebtk.modern.tree.TreeNodeFileCountRenderer;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class ExperimentFilesTreePanel.
 */
public class ExperimentFilesTreePanel extends ModernGradientPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The tree. */
  private ModernTree<FileDescriptor> tree = new ModernTree<FileDescriptor>();

  // private SamplesSortPanel samplesListSortPanel = new SamplesSortPanel();

  // private Experiments experiments;

  /**
   * Instantiates a new experiment files tree panel.
   */
  public ExperimentFilesTreePanel() {
    setup();
  }

  /**
   * Setup.
   */
  private void setup() {

    // setBackground(Color.PINK);

    // samplesListSortPanel.addClickListener(this);

    // add(new ModernHeadingLabel("Experiments"), BorderLayout.PAGE_START);

    TreeNodeFileCountRenderer renderer = new TreeNodeFileCountRenderer();

    renderer.setOpaque(false);

    tree.setNodeRenderer(renderer);
    tree.setOpaque(false);

    // tree.setBorder(RIGHT_BORDER);

    ModernScrollPane scrollPane = new ModernScrollPane(tree);
    scrollPane.setOpaque(false);
    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);
    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    // scrollPane.setOpaque(true);
    // scrollPane.setBackground(Color.WHITE);
    // scrollPane.setBorder(DialogButton.DARK_BORDER);

    setBody(scrollPane);

    // setBorder(AbstractHidePane.HORIZONTAL_BORDER);
  }

  /**
   * Load samples.
   *
   * @param samples
   *          the samples
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParseException
   *           the parse exception
   */
  public void loadSamples(Collection<Sample> samples) throws IOException, ParseException {
    if (samples == null) {
      return;
    }

    Repository repository = RepositoryService.getInstance().getRepository(RepositoryService.DEFAULT_REP);

    Map<Experiment, Set<Sample>> experiments = Experiment.sortSamplesByExperiment(samples);

    List<Experiment> sortedExperiments = CollectionUtils.sort(experiments.keySet());

    TreeRootNode<FileDescriptor> root = new TreeRootNode<FileDescriptor>();

    TreeNode<FileDescriptor> experimentsRoot = new TreeNode<FileDescriptor>("Experiments");

    for (Experiment experiment : sortedExperiments) {
      TreeNode<FileDescriptor> experimentNode = new TreeNode<FileDescriptor>(experiment.getName());

      experimentNode.setIsExpandable(false);

      for (FileDescriptor file : repository.getSampleFiles(experiments.get(experiment))) {

        TreeNode<FileDescriptor> fileNode = new TreeNode<FileDescriptor>(file.getName(), file);

        experimentNode.addChild(fileNode);
      }

      // TreeNode<ArrayDbFileDescriptor> samplesNode =
      // new TreeNode<ArrayDbFileDescriptor>("Samples");

      /*
       * List<SampleSearchResult> sortedSamples = Lists.sort(experiment.getSamples());
       * 
       * for (SampleSearchResult sample : sortedSamples) {
       * TreeNode<ArrayDbFileDescriptor> sampleNode = new
       * TreeNode<ArrayDbFileDescriptor>(sample.getName());
       * 
       * sampleNode.setIsExpandable(false);
       * 
       * sortedFiles = Lists.sort(sample.getExpressionData().getFiles());
       * 
       * for (ArrayDbFileDescriptor file : sortedFiles) {
       * 
       * TreeNode<ArrayDbFileDescriptor> fileNode = new
       * TreeNode<ArrayDbFileDescriptor>(file.getName(), file);
       * 
       * sampleNode.addChild(fileNode); }
       * 
       * //samplesNode.addChild(sampleNode);
       * 
       * experimentNode.addChild(sampleNode); }
       * 
       * //experimentNode.addChild(samplesNode);
       */

      experimentsRoot.addChild(experimentNode);

      // loadOtherFiles(experiment, experimentNode);
    }

    root.addChild(experimentsRoot);

    tree.setRoot(root);

    if (tree.getChildCount() > 0) {
      tree.selectNode(1);
    }
  }

  /**
   * Gets the selected files.
   *
   * @return the selected files
   */
  public List<FileDescriptor> getSelectedFiles() {

    Set<FileDescriptor> fileSet = new HashSet<FileDescriptor>();

    for (TreeNode<FileDescriptor> node : tree.getSelectedNodes()) {
      getFiles(node, fileSet);
    }

    List<FileDescriptor> files = new ArrayList<FileDescriptor>();

    for (FileDescriptor file : fileSet) {
      files.add(file);
    }

    Collections.sort(files);

    return files;
  }

  /**
   * Gets the files.
   *
   * @param node
   *          the node
   * @param files
   *          the files
   * @return the files
   */
  private void getFiles(TreeNode<FileDescriptor> node, Set<FileDescriptor> files) {
    if (node.getValue() != null) {
      files.add(node.getValue());
    }

    if (node.isParent()) {
      for (TreeNode<FileDescriptor> child : node) {
        getFiles(child, files);
      }
    }
  }

  /**
   * Sets the selected sample.
   *
   * @param sample
   *          the new selected sample
   */
  public void setSelectedSample(Sample sample) {
    setSelectedSample(sample.getName());
  }

  /**
   * Sets the selected sample.
   *
   * @param name
   *          the new selected sample
   */
  public void setSelectedSample(String name) {
    setSelectedSample(tree.getNodeIndexByName(name));
  }

  /**
   * Sets the selected sample.
   *
   * @param row
   *          the new selected sample
   */
  public void setSelectedSample(int row) {
    tree.selectNode(row);
  }

  /**
   * Adds the selection listener.
   *
   * @param l
   *          the l
   */
  public void addSelectionListener(ModernSelectionListener l) {
    tree.addSelectionListener(l);
  }
}
