package edu.columbia.rdf.edb.experiments.app.page;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.UI;
import org.jebtk.modern.graphics.ImageUtils;
import org.jebtk.modern.ribbon.Ribbon;


public class PagesPanel extends ModernComponent implements ChangeListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final int ICON_SIZE = 24;

  private int mWidth;

  public PagesPanel() {
    PageService.getInstance().getPagesListener().addChangeListener(this);
    PageService.getInstance().addChangeListener(this);

    addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent e) {
        changePage(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

      }});
    
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    refresh();
  }

  private void refresh() {
    mWidth = PageService.getInstance().getPages() * ICON_SIZE;

    UI.setSize(this, mWidth, ICON_SIZE);
    
    repaint();
  }

  @Override
  public void changed(ChangeEvent e) {
    refresh();
  }

  /*
  @Override
  public void drawBackground(Graphics2D g2) {
    int x = getInsets().left + (PageService.getInstance().getPage() - 1) * ICON_SIZE;

    int y = getInsets().top;

    g2.setColor(LINE_COLOR);

    Graphics2D g2Temp = ImageUtils.createAAStrokeGraphics(g2);

    try {
      g2Temp.drawOval(x + 1, y + 1, ICON_SIZE - 2, ICON_SIZE - 2);
    } finally {
      g2Temp.dispose();
    }
  }
  */

  @Override
  public void drawForeground(Graphics2D g2) {
    int x = getInsets().left;

    int y = ModernWidget.getTextYPosCenter(g2, getHeight());

    Graphics2D g2Temp = ImageUtils.createAAStrokeGraphics(g2);

    
    
    try {
      for (int i = 1; i <= PageService.getInstance().getPages(); ++i) {
        String t = Integer.toString(i);
        
        if (i == PageService.getInstance().getPage()) {
          g2Temp.setColor(Ribbon.BAR_BACKGROUND);
          g2Temp.setFont(ModernWidget.BOLD_FONT);
        } else {
          g2Temp.setColor(ModernWidget.DARK_LINE_COLOR);
          g2Temp.setFont(ModernWidget.FONT);
        }

        int w = ModernWidget.getStringWidth(g2, t);

        int x1 = x + (ICON_SIZE - w) / 2;

        g2Temp.drawString(t, x1, y);

        x += ICON_SIZE;
      }
    } finally {
      g2Temp.dispose();
    }
  }

  private void changePage(MouseEvent e) {
    int i = (e.getX() - getInsets().left) / ICON_SIZE + 1;

    PageService.getInstance().setPage(i);
  }
}
