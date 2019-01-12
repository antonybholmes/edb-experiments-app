package edu.columbia.rdf.edb.experiments.app.cart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;

import edu.columbia.rdf.edb.Sample;

public class SampleCartService extends ChangeListeners
    implements Iterable<Sample>, ChangeListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static class ServiceLoader {
    private static final SampleCartService INSTANCE = new SampleCartService();
  }

  public static SampleCartService getInstance() {
    return ServiceLoader.INSTANCE;
  }

  private List<Sample> mSamples = new UniqueArrayList<Sample>();

  private SampleCartService() {
    addChangeListener(this);
  }

  public void add(Iterable<Sample> samples) {
    update(samples);

    fireChanged();
  }

  public void update(Iterable<Sample> samples) {
    for (Sample s : samples) {
      mSamples.add(s);
    }
  }

  @Override
  public void changed(ChangeEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public Iterator<Sample> iterator() {
    return mSamples.iterator();
  }

  public void clear() {
    mSamples.clear();

    fireChanged();
  }

  public int size() {
    return mSamples.size();
  }

  public void remove(Sample sample) {
    mSamples.remove(sample);

    fireChanged();
  }

  /**
   * Return a copy of the cart samples as a list.
   * 
   * @return
   */
  public List<Sample> toList() {
    return new ArrayList<Sample>(mSamples);
  }
}
