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

import org.jebtk.core.tree.TreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

/**
 * The Class SearchFolderTreeNode.
 */
public class SearchFolderTreeNode extends TreeNode<UserSearch> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new search folder tree node.
   *
   * @param name the name
   */
  public SearchFolderTreeNode(String name) {
    super(name);
  }

  /**
   * Instantiates a new search folder tree node.
   *
   * @param name the name
   * @param userSearch the user search
   */
  public SearchFolderTreeNode(String name, UserSearch userSearch) {
    super(name, userSearch);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.tree.TreeNode#toXml(org.w3c.dom.Document)
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("search-folder");
    element.setAttribute("name", getName());

    for (TreeNode<UserSearch> child : this) {
      element.appendChild(child.toXml(doc));
    }

    if (getValue() != null) {
      for (UserSearchEntry entry : getValue()) {
        element.appendChild(entry.toXml(doc));
      }
    }

    return element;
  }

}
