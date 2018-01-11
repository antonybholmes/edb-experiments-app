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
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.xml.XmlRepresentation;
import org.jebtk.core.xml.XmlUtils;
import org.jebtk.modern.tree.ModernTree;
import org.jebtk.modern.tree.ModernTreeEvent;
import org.jebtk.modern.tree.ModernTreeNodeRenderer;
import org.jebtk.modern.tree.ModernTreeTextNodeRenderer;
import org.jebtk.modern.tree.TreeEventListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.ui.search.UserSearch;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFolderTree.
 */
public class SearchFolderTree extends ModernTree<UserSearch>
    implements XmlRepresentation {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The user file. */
  private static Path USER_FILE = PathUtils.getPath("user.search.folders.xml");

  /** The default file. */
  private static Path DEFAULT_FILE = PathUtils
      .getPath("res/default.search.folders.xml");

  /**
   * The Class TreeEvents.
   */
  public class TreeEvents implements TreeEventListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.tree.TreeEventListener#treeNodeDragged(org.abh.common.
     * ui. tree.ModernTreeEvent)
     */
    @Override
    public void treeNodeDragged(ModernTreeEvent e) {
      // System.err.println("drag " + getSelectedNodes().get(0).getName() + " "
      // +
      // getTargetNode().getName());

      List<TreeNode<UserSearch>> selectedNodes = getSelectedNodes();

      // Search Folders cannot be moved
      for (TreeNode<UserSearch> node : selectedNodes) {
        if (node.getName().equals("Search Folders")) {
          return;
        }
      }

      for (TreeNode<UserSearch> node : selectedNodes) {
        node.getParent().removeChild(node);

        getTargetNode().addChild(node);
      }

      try {
        write();
      } catch (IOException | TransformerException
          | ParserConfigurationException e1) {
        e1.printStackTrace();
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.tree.TreeEventListener#treeNodeClicked(org.abh.common.
     * ui. tree.ModernTreeEvent)
     */
    @Override
    public void treeNodeClicked(ModernTreeEvent e) {
      // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.tree.TreeEventListener#treeNodeDoubleClicked(org.abh.
     * common .ui.tree.ModernTreeEvent)
     */
    @Override
    public void treeNodeDoubleClicked(ModernTreeEvent e) {
      // TODO Auto-generated method stub

    }

  }

  /**
   * Instantiates a new search folder tree.
   */
  public SearchFolderTree() {
    addTreeListener(new TreeEvents());
  }

  /**
   * Adds the search folder.
   *
   * @param name the name
   * @param userSearch the user search
   */
  public void addSearchFolder(String name, UserSearch userSearch) {
    TreeNode<UserSearch> child = new SearchFolderTreeNode(name, userSearch);

    getRoot().getChild("Search Folders").addChild(child);

    try {
      write();
    } catch (IOException | TransformerException
        | ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Delete folder.
   */
  public void deleteFolder() {
    for (TreeNode<UserSearch> node : getSelectedNodes()) {
      if (node.getName().equals("Search Folders")) {
        continue;
      }

      node.getParent().removeChild(node);
    }

    try {
      write();
    } catch (IOException | TransformerException
        | ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.xml.XmlRepresentation#toXml(org.w3c.dom.Document)
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("search-folders");

    for (TreeNode<UserSearch> node : getRoot().getChild(0)) {
      element.appendChild(node.toXml(doc));
    }

    return element;
  }

  /**
   * Write.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws TransformerException the transformer exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  public void write()
      throws IOException, TransformerException, ParserConfigurationException {
    Document doc = XmlUtils.createDoc();

    doc.appendChild(toXml(doc));

    XmlUtils.writeXml(doc, USER_FILE);
  }

  /**
   * Checks for the existance of the default search file and then creates one if
   * necessary.
   *
   * @return the modern tree
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static ModernTree<UserSearch> autoLoad()
      throws IOException, SAXException, ParserConfigurationException {
    if (!FileUtils.exists(USER_FILE)) {
      if (FileUtils.exists(DEFAULT_FILE)) {
        FileUtils.copy(DEFAULT_FILE, USER_FILE);
      }
    }

    if (FileUtils.exists(USER_FILE)) {
      return loadXml(USER_FILE);
    } else {
      return null;
    }
  }

  /**
   * Load xml.
   *
   * @param file the file
   * @return the modern tree
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static ModernTree<UserSearch> loadXml(Path file)
      throws SAXException, IOException, ParserConfigurationException {
    InputStream stream = FileUtils.newBufferedInputStream(file);

    return loadXml(stream);
  }

  /**
   * Load xml.
   *
   * @param is the is
   * @return the modern tree
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  private synchronized static ModernTree<UserSearch> loadXml(InputStream is)
      throws SAXException, IOException, ParserConfigurationException {
    if (is == null) {
      return null;
    }

    ModernTree<UserSearch> tree = new SearchFolderTree();

    ModernTreeNodeRenderer renderer = new ModernTreeTextNodeRenderer();

    renderer.setBranchHeight(28);

    tree.setNodeRenderer(renderer);

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    SearchFolderXmlHandler handler = new SearchFolderXmlHandler();

    saxParser.parse(is, handler);

    // TreeRootNode<UserSearch> root = new TreeRootNode<UserSearch>();

    // root.addChild(handler.getSearchFolders());

    tree.setRoot(handler.getSearchFolders()); // root);

    return tree;
  }

}
