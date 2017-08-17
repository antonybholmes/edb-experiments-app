package edu.columbia.rdf.edb.experiments.app;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

import javax.swing.SwingWorker;

import org.jebtk.bioinformatics.annotation.Type;

import edu.columbia.rdf.edb.Groups;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.Species;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.search.ArraySearchRT;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;

/**
 * The Class SearchCategoryTask.
 */
public class SearchCategoryTask extends SwingWorker<Void, Void> {

	/** The m search stack. */
	private Deque<SearchStackElementCategory> mSearchStack;

	/** The m samples. */
	private List<Sample> mSamples;

	/** The m data types. */
	private Collection<Type> mDataTypes;

	/** The m organisms. */
	private Collection<Species> mOrganisms;
	
	private SampleModel mSampleSearchModel;

	private Groups mGroups;

	/**
	 * Instantiates a new search category task.
	 *
	 * @param searchStack the search stack
	 * @param dataTypes the data types
	 * @param organisms the organisms
	 */
	public SearchCategoryTask(SampleModel model,
			Deque<SearchStackElementCategory> searchStack,
			Collection<Type> dataTypes,
			Collection<Species> organisms,
			Groups groups) {
		mSampleSearchModel = model;
		mSearchStack = searchStack;
		mDataTypes = dataTypes;
		mOrganisms = organisms;
		mGroups = groups;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	public Void doInBackground() {

		ArraySearchRT search = new ArraySearchRT();

		// find a list of relevant experiments
		try {
			mSamples = search.searchSamples(mSearchStack, 
					mDataTypes, 
					mOrganisms,
					mGroups);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	public void done() {
		mSampleSearchModel.set(mSamples);
	}
}