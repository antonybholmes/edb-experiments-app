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
package edu.columbia.rdf.edb.experiments.app.vfs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.tree.TreeRootNode;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.tree.ModernTree;
import org.jebtk.modern.tree.ModernTreeEvent;
import org.jebtk.modern.tree.TreeEventAdapter;
import org.jebtk.modern.tree.TreeNodeFileRenderer;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.FileType;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.files.FilesModel;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class VfsFilesTreePanel.
 */
public class VfsFilesTreePanel extends ModernComponent {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m tree. */
  private ModernTree<FileDescriptor> mTree = new ModernTree<FileDescriptor>();

  /** The m files model. */
  private FilesModel mFilesModel;

  // private Map<Experiment, Collection<Sample>> mExperiments;

  /**
   * The Class TreeEvents.
   */
  private class TreeEvents extends TreeEventAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.tree.TreeEventAdapter#treeNodeClicked(org.abh.common.
     * ui. tree.ModernTreeEvent)
     */
    @Override
    public void treeNodeClicked(ModernTreeEvent e) {
      try {
        selectFiles();
      } catch (ParseException | IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Instantiates a new vfs files tree panel.
   *
   * @param filesModel the files model
   * @param experiments the experiments
   */
  public VfsFilesTreePanel(FilesModel filesModel,
      Map<Experiment, Set<Sample>> experiments) {
    mFilesModel = filesModel;
    // mExperiments = experiments;

    setup();
  }

  /**
   * Setup.
   */
  private void setup() {

    // setBackground(Color.PINK);

    // samplesListSortPanel.addClickListener(this);

    // add(new ModernHeadingLabel("Experiments"), BorderLayout.PAGE_START);

    TreeNodeFileRenderer renderer = new TreeNodeFileRenderer();

    // renderer.setOpaque(false);

    // mTree.setAllowMultiSelect(false);

    mTree.setNodeRenderer(renderer);
    // mTree.setOpaque(false);

    setBorder(BORDER);

    ModernScrollPane scrollPane = new ModernScrollPane(mTree);
    // scrollPane.setOpaque(false);
    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);
    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    // scrollPane.setOpaque(true);
    // scrollPane.setBackground(Color.WHITE);
    // scrollPane.setBorder(DialogButton.DARK_BORDER);

    setBody(scrollPane);

    try {
      loadDirs();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    mTree.addTreeListener(new TreeEvents());
  }

  /**
   * Select files.
   *
   * @throws MalformedURLException the malformed URL exception
   * @throws ParseException the parse exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void selectFiles()
      throws MalformedURLException, ParseException, IOException {
    if (mTree.getSelectedNode().getChildCount() == 0) {
      int nid = mTree.getSelectionModel().first();

      TreeNode<FileDescriptor> node = mTree.getSelectedNode();

      loadDirs(
          RepositoryService.getInstance()
              .getRepository(RepositoryService.DEFAULT_REP),
          node);

      // node.setExpanded(false);

      // Since updating the tree in place causes layout changes
      // and removes selections,
      mTree.getSelectionModel().setSelection(nid);
    }

    setFiles();
  }

  /**
   * Load dirs.
   *
   * @throws ParseException the parse exception
   * @throws MalformedURLException the malformed URL exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void loadDirs()
      throws ParseException, MalformedURLException, IOException {

    Repository store = RepositoryService.getInstance()
        .getRepository(RepositoryService.DEFAULT_REP);

    /*
     * TreeNode<FileDescriptor> root = new TreeNode<FileDescriptor>("/"); if
     * (mExperiments.size() > 0) { for (Experiment experiment :
     * mExperiments.keySet()) { FileDescriptor file =
     * store.getExperimentFilesDir(experiment);
     * 
     * TreeNode<FileDescriptor> expNode = new
     * TreeNode<FileDescriptor>(experiment.getName(), file);
     * 
     * expNode.setIsParent(true);
     * 
     * root.addChild(expNode);
     * 
     * loadDirs(store, expNode); } } else {
     */

    TreeRootNode<FileDescriptor> root = new TreeRootNode<FileDescriptor>();

    for (FileDescriptor file : store.vfs().ls()) {
      if (file.getType() == FileType.DIR) {
        TreeNode<FileDescriptor> fileNode = new TreeNode<FileDescriptor>(
            file.getName(), file);

        fileNode.setIsParent(true);

        // add first level children
        loadDirs(store, fileNode);

        root.addChild(fileNode);
      }
    }
    // }

    mTree.setRoot(root);

    mTree.getRoot().setChildrenAreExpanded(true, true);

    // if (mTree.getChildCount() > 0) {
    // mTree.selectNode(1);
    // }
  }

  /**
   * Load dirs.
   *
   * @param store the store
   * @param node the node
   * @throws ParseException the parse exception
   * @throws MalformedURLException the malformed URL exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void loadDirs(Repository store, TreeNode<FileDescriptor> node)
      throws ParseException, MalformedURLException, IOException {
    for (FileDescriptor file : store.vfs().ls(node.getValue().getId())) {

      if (file.getType() == FileType.DIR) {
        TreeNode<FileDescriptor> fileNode = new TreeNode<FileDescriptor>(
            file.getName(), file);

        fileNode.setIsParent(true);

        // add first level children

        node.addChild(fileNode);
      }
    }
  }

  /**
   * Gets the selected files.
   *
   * @return the selected files
   */
  public List<FileDescriptor> getSelectedFiles() {

    Set<FileDescriptor> fileSet = new HashSet<FileDescriptor>();

    for (TreeNode<FileDescriptor> node : mTree.getSelectedNodes()) {
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
   * @param node the node
   * @param files the files
   * @return the files
   */
  private void getFiles(TreeNode<FileDescriptor> node,
      Set<FileDescriptor> files) {
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
   * @param sample the new selected sample
   */
  public void setSelectedSample(Sample sample) {
    setSelectedSample(sample.getName());
  }

  /**
   * Sets the selected sample.
   *
   * @param name the new selected sample
   */
  public void setSelectedSample(String name) {
    setSelectedSample(mTree.getNodeIndexByName(name));
  }

  /**
   * Sets the selected sample.
   *
   * @param row the new selected sample
   */
  public void setSelectedSample(int row) {
    mTree.selectNode(row);
  }

  /**
   * Adds the selection listener.
   *
   * @param l the l
   */
  public void addSelectionListener(ModernSelectionListener l) {
    mTree.addSelectionListener(l);
  }

  /**
   * Sets the files.
   *
   * @throws ParseException the parse exception
   * @throws MalformedURLException the malformed URL exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void setFiles()
      throws ParseException, MalformedURLException, IOException {
    FileDescriptor file = mTree.getSelectedNode().getValue();

    if (file != null) {
      Repository repository = RepositoryService.getInstance()
          .getRepository(RepositoryService.DEFAULT_REP);

      List<FileDescriptor> files = repository.vfs()
          .ls(mTree.getSelectedNode().getValue().getId());

      Collections.sort(files);

      // Organize folders first

      List<FileDescriptor> ret = new ArrayList<FileDescriptor>();
      List<FileDescriptor> f2 = new ArrayList<FileDescriptor>();

      for (FileDescriptor f : files) {
        if (f.getType() == FileType.DIR) {
          ret.add(f);
        } else {
          f2.add(f);
        }
      }

      ret.addAll(f2);

      mFilesModel.set(ret);
    }
  }
}
