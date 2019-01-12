package edu.columbia.rdf.edb.experiments.app.page;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.UI;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernNumericalTextField;
import org.jebtk.modern.text.SearchTextBorderPanel;

public class SimplePagesPanel extends HBox implements ChangeListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ModernNumericalTextField mPageField = new ModernNumericalTextField("1");
  
  private ModernAutoSizeLabel mPagesLabel = new ModernAutoSizeLabel("1", 50);

  public SimplePagesPanel() {
    PageService.getInstance().addChangeListener(this);
    PageService.getInstance().getPagesListener().addChangeListener(this);

    add(new SearchTextBorderPanel(mPageField, 40));
    add(UI.createHGap(4));
    add(new ModernAutoSizeLabel("/"));
    add(UI.createHGap(5));
    add(mPagesLabel);
    
    refresh();
    
    mPageField.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          changePage();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
      }});
  }

  private void refresh() {
    mPageField.setText(PageService.getInstance().getPage());
    mPagesLabel.setText(PageService.getInstance().getPages());
  }

  @Override
  public void changed(ChangeEvent e) {
    refresh();
  }

  private void changePage() {
    PageService.getInstance().setPage(mPageField.getInt());
  }
  
  @Override
  public Dimension getPreferredSize() {
    int w = 0;
    
    for (int i = 0; i < this.getComponentCount(); ++i) {
      w += getComponent(i).getPreferredSize().width;
    }
    
    return new Dimension(w, super.getPreferredSize().height);
  }
  
  @Override
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
}
