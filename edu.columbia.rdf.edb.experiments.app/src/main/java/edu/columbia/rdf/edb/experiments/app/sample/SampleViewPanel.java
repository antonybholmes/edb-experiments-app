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
package edu.columbia.rdf.edb.experiments.app.sample;

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.tabs.TabsViewPanel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.SelectedSamples;

// TODO: Auto-generated Javadoc
/**
 * In charge of showing the sample view.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class SampleViewPanel extends ModernPanel implements SelectedSamples {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The m annotation vertical panel. */
	private SampleViewVerticalPanel mAnnotationVerticalPanel;
	//private ExperimentsTreePanel experimentsPanel;

	/** The m tabs panel. */
	private TabsViewPanel mTabsPanel = new TabsViewPanel();

	//private ModernRowTable mViewDetails = new ModernRowTable();
	//private ModernDataListView mViewList = new ModernDataListView();
	//private ModernDataTileView mViewTiles = new ModernDataTileView();

	/** The m layout view model. */
	private ViewModel mLayoutViewModel;

	/** The m annotation wide panel. */
	private SampleViewWidePanel mAnnotationWidePanel;

	/** The m filtered samples. */
	private List<Sample> mFilteredSamples;

	/** The m sample model. */
	private SampleModel mSampleModel;

	/**
	 * The Class ViewEvents.
	 */
	private class ViewEvents implements ChangeListener {

		/* (non-Javadoc)
		 * @see org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
		 */
		@Override
		public void changed(ChangeEvent e) {
			viewChanged();
		}
	}

	/**
	 * Instantiates a new sample view panel.
	 *
	 * @param parent the parent
	 * @param viewModel the view model
	 * @param layoutViewModel the layout view model
	 * @param sampleModel the sample model
	 * @param sampleSelectionModel the sample selection model
	 * @param fileViewModel the file view model
	 */
	public SampleViewPanel(ModernWindow parent,
			ViewModel viewModel,
			ViewModel layoutViewModel,
			SampleModel sampleModel,
			SampleModel sampleSelectionModel,
			FilterModel filterModel,
			ViewModel fileViewModel) {
		mLayoutViewModel = layoutViewModel;
		mSampleModel = sampleModel;


		mAnnotationVerticalPanel = new SampleViewVerticalPanel(parent,
				viewModel,
				sampleModel,
				sampleSelectionModel,
				filterModel,
				fileViewModel);

		mAnnotationWidePanel = new SampleViewWidePanel(parent,
				viewModel,
				sampleModel,
				sampleSelectionModel,
				filterModel,
				fileViewModel);


		//sampleModel.addSelectionListener(new SelectionEvents());

		mTabsPanel.getTabsModel().addTab("Vertical", mAnnotationVerticalPanel);
		mTabsPanel.getTabsModel().addTab("Wide", mAnnotationWidePanel);
		//mTabsPanel.getTabsModel().addTab("Details", new ModernScrollPane(mViewDetails));

		//mTabsPanel.getTabsModel().addTab("List", new ModernScrollPane(mViewList));
		//mTabsPanel.getTabsModel().addTab("Tiles", new ModernScrollPane(mViewTiles));


		setBody(mTabsPanel);

		setBorder(TOP_BORDER);

		layoutViewModel.addChangeListener(new ViewEvents());
	}

	/**
	 * Filter samples.
	 */
	private final void filterSamples() {

		if (mSampleModel.size() == 0) {
			return;
		}

		mFilteredSamples = new ArrayList<Sample>();

		String expressionType = 
				mSampleModel.get(0).getExpressionType().getName();

		// When viewing samples in a table, they must all be of the
		// same type, so take the type of the first and keep only those
		// samples that match its type.
		for (Sample sample : mSampleModel.getItems()) {
			if (!sample.getExpressionType().equals(expressionType)) {
				continue;
			}

			mFilteredSamples.add(sample);
		}
	}

	/**
	 * View changed.
	 */
	private void viewChanged() {
		mTabsPanel.getTabsModel().changeTab(mLayoutViewModel.getView());

		filterSamples();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.edb.ui.SelectedSamples#getSelectedSamples()
	 */
	@Override
	public List<Sample> getSelectedSamples() {

		switch (mTabsPanel.getTabsModel().getSelectedIndex()) {
		case 0:
			return mAnnotationVerticalPanel.getSamplesPanel().getSelectedSamples();
		case 1:
			return mAnnotationWidePanel.getSamplesPanel().getSelectedSamples();
		default:
			return null;
		}
	}
}
