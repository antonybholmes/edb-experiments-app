package edu.columbia.rdf.edb.experiments.app;

import java.io.IOException;
import java.util.Collection;
import java.util.Deque;

import javax.swing.SwingWorker;

import org.jebtk.bioinformatics.annotation.Type;

import edu.columbia.rdf.edb.Group;
import edu.columbia.rdf.edb.Species;
import edu.columbia.rdf.edb.experiments.app.page.PageService;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;
import edu.columbia.rdf.edb.ui.SearchResults;
import edu.columbia.rdf.edb.ui.filter.sets.SetsService;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;

/**
 * The Class SearchCategoryTask.
 */
public class SearchCategoryTask extends SwingWorker<Void, Void> {

  /** The m search stack. */
  private Deque<SearchStackElementCategory> mSearchStack;

  /** The m samples. */
  private SearchResults mSamples;

  /** The m data types. */
  private Collection<Type> mDataTypes;

  /** The m organisms. */
  private Collection<Species>  mOrganisms;

  private SampleModel mSampleSearchModel;

  private Collection<Group> mGroups;

  /**
   * Instantiates a new search category task.
   *
   * @param searchStack the search stack
   * @param dataTypes the data types
   * @param organisms the organisms
   */
  public SearchCategoryTask(SampleModel model,
      Deque<SearchStackElementCategory> searchStack, Collection<Type> dataTypes,
      Collection<Species> organisms, Collection<Group> groups) {
    mSampleSearchModel = model;
    mSearchStack = searchStack;
    mDataTypes = dataTypes;
    mOrganisms = organisms;
    mGroups = groups;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.SwingWorker#doInBackground()
   */
  @Override
  public Void doInBackground() {
    
    
    Repository store = RepositoryService.getInstance().getRepository();
    
    
    
    if (mSearchStack.size() == 0 || (mSearchStack.size() == 1
        && mSearchStack.peek().getSearch() == null)) {
      try {
        mSamples = store.getAllSamples(SetsService.getInstance().getSampleSets(), PageService.getInstance().getPage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    SearchStackElementCategory op = mSearchStack.pop();

    try {
      mSamples = store.searchSamples(op.getSearch().search,
          op.getSearchField().getPath(),
          mDataTypes,
          mOrganisms,
          mGroups,
          SetsService.getInstance().getSampleSets(),
          PageService.getInstance().getPage());
    } catch (IOException e) {
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
    mSampleSearchModel.set(mSamples.samples);
    
    PageService.getInstance().set(mSamples.metaData);
  }
}