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
package edu.columbia.rdf.edb.experiments.app.search.folder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingWorker;
import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.tree.ModernTree;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.search.ArraySearchRT;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;

// TODO: Auto-generated Javadoc
/**
 * Displays groupings of samples so users can quickly find related samples.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class SearchFolderTreePanel extends ModernComponent {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant NO_SAMPLES. */
  private static final List<Sample> NO_SAMPLES = Collections
      .unmodifiableList(new ArrayList<Sample>());

  /** The m tree. */
  private ModernTree<UserSearch> mTree;

  // private List<SampleFilterPanel> SampleFilters = new
  // ArrayList<SampleFilterPanel>();

  // private ModernPopupMenu menu;

  // private ModernIconMenuItem expandMenuItem =
  // new ModernIconMenuItem("Expand All",
  // UIResources.getInstance().loadIcon(PlusVectorIcon.class, 16));

  // private ModernIconMenuItem collapseMenuItem =
  // new ModernIconMenuItem("Collapse All",
  // UIResources.getInstance().loadIcon(MinusVectorIcon.class, 16));

  // private ModernIconMenuItem sortMenuItem = new ModernCheckBoxMenuItem("Sort
  // Descending");

  /** The m sample model. */
  private SampleModel mSampleModel;

  /** The m task. */
  private SearchCategoryTask mTask;

  /**
   * The Class TreeEvents.
   */
  private class TreeEvents implements ModernSelectionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
     * common.event.ChangeEvent)
     */
    @Override
    public void selectionChanged(ChangeEvent e) {
      search();
    }
  }

  /**
   * The Class SearchCategoryTask.
   */
  private class SearchCategoryTask extends SwingWorker<Void, Void> {

    /** The m samples. */
    private List<Sample> mSamples = null;

    // private MessageDialogTaskGlassPane mSearchScreen;

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    public Void doInBackground() {

      // mSearchScreen = mParent.createTaskDialog("Searching...");

      // searchButton.setEnabled(false);

      try {
        mSamples = getSelectedSamples();
      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.SwingWorker#done()
     */
    @Override
    public void done() {
      mSampleModel.set(mSamples);
    }
  }

  /**
   * Instantiates a new search folder tree panel.
   *
   * @param sampleModel the sample model
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public SearchFolderTreePanel(SampleModel sampleModel)
      throws SAXException, IOException, ParserConfigurationException {
    mSampleModel = sampleModel;

    mTree = SearchFolderTree.autoLoad();

    // mSampleViewModel.addChangeListener(new SortEvents());

    setup();
  }

  /**
   * Setup.
   */
  private void setup() {

    // setHeader(new ModernSubHeadingLabel("Search Folders",
    // UI.createBottomBorder(20)));

    ModernScrollPane scrollPane = new ModernScrollPane(mTree);
    // scrollPane.setBorder(BORDER);
    scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.AUTO_SHOW);
    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);

    setBody(scrollPane);

    // setBorder(DOUBLE_BORDER);

    mTree.addSelectionListener(new TreeEvents());
  }

  /**
   * Search.
   */
  private void search() {
    if (mTree.getSelectedNodes().size() == 0) {
      return;
    }

    System.err.println("Searching");

    if (mTask != null) {
      // If a search task is currently running, cancel it to stop
      // asynchronous results loading.
      mTask.cancel(true);
    }

    // Start a new search
    mTask = new SearchCategoryTask();

    mTask.execute();
  }

  /**
   * Adds the search folder.
   *
   * @param name the name
   * @param userSearch the user search
   */
  public void addSearchFolder(String name, UserSearch userSearch) {
    ((SearchFolderTree) mTree).addSearchFolder(name, userSearch);
  }

  /**
   * Delete folder.
   */
  public void deleteFolder() {
    ((SearchFolderTree) mTree).deleteFolder();
  }

  // public void addSelectionListener(ModernSelectionListener l) {
  // mTree.addSelectionListener(l);
  // }

  /**
   * Gets the selected samples.
   *
   * @return the selected samples
   * @throws Exception the exception
   */
  private List<Sample> getSelectedSamples() throws Exception {
    if (mTree.getSelectedNodes().size() == 0) {
      return NO_SAMPLES; // new ArrayList<ExperimentSearchResult>();
    }

    List<UserSearch> folders = new ArrayList<UserSearch>(10);

    for (TreeNode<UserSearch> node : mTree.getSelectedNodes()) {
      selectedSamples(node, folders);
    }

    System.err.println("folders " + folders.size());

    ArraySearchRT search = new ArraySearchRT();

    Set<Sample> samples = new HashSet<Sample>();

    for (UserSearch folder : folders) {
      Deque<SearchStackElementCategory> stack = SearchStackElementCategory
          .getSearchStack(folder);

      System.err.println("stack " + stack.size());

      // find a list of relevant experiments
      try {
        samples.addAll(search.searchSamples(stack));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return CollectionUtils.sort(samples);
  }

  /**
   * Recursively examine a node and its children to find those with experiments.
   *
   * @param node the node
   * @param folders the folders
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void selectedSamples(TreeNode<UserSearch> node,
      List<UserSearch> folders) throws IOException {

    if (node.getValue() != null) {
      folders.add(node.getValue());
    }

    for (TreeNode<UserSearch> child : node) {
      if (child.getValue() != null) {
        selectedSamples(child, folders);
      }
    }
  }

  /**
   * Sets the selected.
   *
   * @param i the new selected
   */
  public void setSelected(int i) {
    mTree.selectNode(i);
  }

  /**
   * Gets the search folder.
   *
   * @return the search folder
   */
  public UserSearch getSearchFolder() {
    TreeNode<UserSearch> selected = mTree.getSelectedNode();

    return selected.getValue() != null ? selected.getValue() : null;
  }

  /**
   * Gets the selected node.
   *
   * @return the selected node
   */
  public TreeNode<UserSearch> getSelectedNode() {
    return mTree.getSelectedNode();
  }
}
