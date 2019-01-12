package edu.columbia.rdf.edb.experiments.app.page;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.HCenterBox;
import org.jebtk.modern.ribbon.RibbonRoundButton;
import org.jebtk.modern.widget.ModernClickWidget;
import org.jebtk.modern.window.ModernWindow;

public class PagePanel extends HCenterBox implements ChangeListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ModernClickWidget mPrevButton = new RibbonRoundButton(
      AssetService.getInstance().loadIcon("left_arrow", 16));

  /**
   * The member move right button.
   */
  private ModernClickWidget mNextButton = new RibbonRoundButton(
      AssetService.getInstance().loadIcon("right_arrow", 16));

  private PagesPanel mPagesPanel = new PagesPanel();

  private SimplePagesPanel mSimplePagesPanel = new SimplePagesPanel();

  private Box mContentBox = new HBox();

  public PagePanel(ModernWindow parent) {
    add(mContentBox);

    setBorder(ModernComponent.DOUBLE_BORDER);

    mContentBox.add(mPrevButton);
    mContentBox.add(UI.createHGap(ModernComponent.PADDING));
    mContentBox.add(mPagesPanel);
    mContentBox.add(UI.createHGap(ModernComponent.PADDING));
    mContentBox.add(mNextButton);

    mPrevButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        PageService.getInstance().setPage(PageService.getInstance().getPage() - 1);
      }});

    mNextButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        PageService.getInstance().setPage(PageService.getInstance().getPage() + 1);
      }});

    PageService.getInstance().getPagesListener().addChangeListener(this);
    
    adjust();
  }

  @Override
  public void changed(ChangeEvent e) {
    adjust();
  }

  private void adjust() {
    mContentBox.removeAll();

    if (PageService.getInstance().getPages() <= 20) {
      mContentBox.add(mPrevButton);
      mContentBox.add(UI.createHGap(ModernComponent.PADDING));
      mContentBox.add(mPagesPanel);
      mContentBox.add(UI.createHGap(ModernComponent.PADDING));
      mContentBox.add(mNextButton);
    } else {
      mContentBox.add(mPrevButton);
      mContentBox.add(UI.createHGap(ModernComponent.PADDING));
      mContentBox.add(mSimplePagesPanel);
      mContentBox.add(UI.createHGap(ModernComponent.PADDING));
      mContentBox.add(mNextButton);
    }
    
    mPrevButton.setEnabled(PageService.getInstance().getPages() > 1);
    mNextButton.setEnabled(mPrevButton.isEnabled());
  }
}
