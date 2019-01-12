package edu.columbia.rdf.edb.experiments.app.page;

import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;

import edu.columbia.rdf.edb.ui.SearchMetaData;

public class PageService extends ChangeListeners {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static class ServiceLoader {
    private static final PageService INSTANCE = new PageService();
  }
  
  private ChangeListeners mPagesListener = new ChangeListeners();

  public static PageService getInstance() {
    return ServiceLoader.INSTANCE;
  }

  private int mPage = 1;
  private int mPages = 1;

  private PageService() {
    // Do nothing
  }

  public void set(int page, int pages) {
    mPage = page;
    mPages = pages;

    // Only update that pages have changed
    mPagesListener.fireChanged();
  }

  public int getPage() {
    return mPage;
  }
  
  public int getPages() {
    return mPages;
  }

  public void setPage(int page) {
    if (page >= 1 && page <= mPages && page != mPage) {
      mPage = page;
      
      fireChanged();
    }
  }

  public void set(SearchMetaData metaData) {
    set(metaData.page, metaData.pages);
  }
  
  public ChangeListeners getPagesListener() {
    return mPagesListener;
  }
}
