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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.UIService;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.list.ModernListModel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;

import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesListPanel.
 */
public class FilesListPanel extends FilesPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The m list. */
	private ModernList<FileDescriptor> mList = 
			new ModernList<FileDescriptor>(new FilesListCellRenderer());
	
	/** The m model. */
	private ModernListModel<FileDescriptor> mModel;
	

	/**
	 * Instantiates a new files list panel.
	 */
	public FilesListPanel() {
		mList.setCellRenderer(new FilesListCellRenderer());
		mList.setRowHeight(UIService.ICON_SIZE_48);
		mList.setBorder(RIGHT_BORDER);
		
		ModernScrollPane scrollPane = new ModernScrollPane(mList);
		//scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);

		//setBorder(AbstractHidePane.VERTICAL_BORDER);
		
		setBody(scrollPane);
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.edb.experiments.app.files.FilesPanel#setSampleFiles(java.util.Collection)
	 */
	public final void setSampleFiles(Collection<Sample> samples) throws IOException, ParseException {
		super.setSampleFiles(samples);
		
		mModel = new ModernListModel<FileDescriptor>();
		
		for (FileDescriptor file : CollectionUtils.sort(mFiles)) {
			mModel.addValue(file);
		}

		mList.setModel(mModel);
	}
	
	/* (non-Javadoc)
	 * @see edu.columbia.rdf.edb.experiments.app.files.FilesPanel#getSelectedFiles()
	 */
	@Override
	public Set<FileDescriptor> getSelectedFiles() {
		Set<FileDescriptor> ret = new HashSet<FileDescriptor>();
		
		for (int i : mList.getSelectionModel()) {
			ret.add(mModel.getValueAt(i));
		}
		
		return ret;
	}
}
