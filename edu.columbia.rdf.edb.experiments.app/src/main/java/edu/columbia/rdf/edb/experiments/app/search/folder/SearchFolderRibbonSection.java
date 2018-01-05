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
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.dialog.DialogEvent;
import org.jebtk.modern.dialog.DialogEventListener;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.window.ModernRibbonWindow;

import edu.columbia.rdf.edb.ui.search.UserSearch;

// TODO: Auto-generated Javadoc
/**
 * Allows user to customize how the experiments are listed.
 *
 * @author Antony Holmes Holmes
 *
 */
public class SearchFolderRibbonSection extends RibbonSection implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant DOWNLOAD. */
  public static final String DOWNLOAD = "Download";

  /** The m new folder button. */
  private ModernButtonWidget mNewFolderButton = new RibbonLargeButton("New Folder",
      UIService.getInstance().loadIcon(SearchFolderVectorIcon.class, 24), "New Search Folder",
      "Create a new search folder.");

  /** The m edit folder button. */
  private ModernButtonWidget mEditFolderButton = new RibbonLargeButton("Edit Folder",
      UIService.getInstance().loadIcon("edit_bw", 16), "Edit Search Folder", "Edit the selected search folder.");

  /** The m delete folder button. */
  private ModernButtonWidget mDeleteFolderButton = new RibbonLargeButton("Delete Folders",
      UIService.getInstance().loadIcon("trash_bw", 16), "Delete Search Folders", "Delete selected search folders.");

  /** The m parent. */
  private ModernRibbonWindow mParent;

  /** The m folder panel. */
  private SearchFolderTreePanel mFolderPanel;

  /**
   * The Class DeleteEvent.
   */
  private class DeleteEvent implements DialogEventListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.dialog.DialogEventListener#statusChanged(org.abh.common.ui.
     * dialog.DialogEvent)
     */
    @Override
    public void statusChanged(DialogEvent e) {
      if (e.getStatus() == ModernDialogStatus.OK) {
        mFolderPanel.deleteFolder();
      }
    }

  }

  /**
   * Instantiates a new search folder ribbon section.
   *
   * @param parent
   *          the parent
   * @param folderPanel
   *          the folder panel
   */
  public SearchFolderRibbonSection(ModernRibbonWindow parent, SearchFolderTreePanel folderPanel) {
    super(parent.getRibbon(), "Search Folders");

    mParent = parent;
    mFolderPanel = folderPanel;

    add(mNewFolderButton);
    // add(mEditFolderButton);
    add(mDeleteFolderButton);

    mNewFolderButton.addClickListener(this);
    mEditFolderButton.addClickListener(this);
    mDeleteFolderButton.addClickListener(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mNewFolderButton)) {
      SearchFolderDialog dialog = new SearchFolderDialog(mParent);

      dialog.setVisible(true);

      if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
        return;
      }

      mFolderPanel.addSearchFolder(dialog.getSearchFolderName(), dialog.getUserSearch());
    } else if (e.getSource().equals(mEditFolderButton)) {
      TreeNode<UserSearch> node = mFolderPanel.getSelectedNode();

      if (node.getValue() != null) {

        SearchFolderDialog dialog = new SearchFolderDialog(mParent, node.getName(), node.getValue());

        dialog.setVisible(true);

        if (dialog.getStatus() == ModernDialogStatus.OK) {
          node.setName(dialog.getSearchFolderName());
        }
      }
    } else if (e.getSource().equals(mDeleteFolderButton)) {
      mParent.createOkCancelDialog("Are you sure you want to delete the selected search folders?", new DeleteEvent());
    }
  }
}
