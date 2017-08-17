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
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.tabs.TabsModel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.experiments.app.files.FilesDataViewGridModel;
import edu.columbia.rdf.edb.experiments.app.files.FilesModel;
import edu.columbia.rdf.edb.experiments.app.files.FilesMultiViewPanel;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.DownloadManager;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class VfsFilesPanel.
 */
public class VfsFilesPanel extends ModernPanel implements ChangeListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The m table. */
	private FilesMultiViewPanel mTable;

	/** The m parent. */
	private ModernWindow mParent;

	/** The m files model. */
	private FilesModel mFilesModel;

	/** The m files. */
	private List<FileDescriptor> mFiles;


	/**
	 * Instantiates a new vfs files panel.
	 *
	 * @param parent the parent
	 * @param filesModel the files model
	 * @param viewModel the view model
	 */
	public VfsFilesPanel(ModernWindow parent,
			FilesModel filesModel,
			ViewModel viewModel) {
		mParent = parent;
		mFilesModel = filesModel;
		
		mFilesModel.addChangeListener(this);

		mTable = new FilesMultiViewPanel(viewModel);
		
		createUi();
	}

	/**
	 * Creates the ui.
	 */
	public final void createUi() {
		setBody(mTable);
		
		setBorder(BORDER);
	}

	/**
	 * Display filtered files.
	 *
	 * @param files the files
	 */
	private void displayFilteredFiles(Collection<FileDescriptor> files) {
		mFiles = CollectionUtils.sort(files);
		
		mTable.setModel(new FilesDataViewGridModel(mFiles));
	}

	/**
	 * Download files.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void downloadFiles() throws IOException {

		Set<FileDescriptor> files = new TreeSet<FileDescriptor>();

		for (int i : mTable.getCellSelectionModel().getRowSelectionModel()) {
			files.add(mFiles.get(mTable.convertRowIndexToModel(i)));
		}

		DownloadManager.downloadAsZip(mParent, files);
	}
	

	/**
	 * Gets the tabs model.
	 *
	 * @return the tabs model
	 */
	public TabsModel getTabsModel() {
		return mTable.getTabsModel();
	}

	/* (non-Javadoc)
	 * @see org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		displayFilteredFiles(mFilesModel);
	}

	/**
	 * Sets the files.
	 *
	 * @param selectionModel the new files
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public void setFiles(SampleModel selectionModel) throws IOException, ParseException {
		if (selectionModel.size() > 0) {
			Repository repository = RepositoryService
					.getInstance()
					.getRepository(RepositoryService.DEFAULT_REP);
			
			displayFilteredFiles(repository.getSampleFiles(selectionModel.getItems()));
		}
	}
}
