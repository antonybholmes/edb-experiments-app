package edu.columbia.rdf.edb.experiments.app.cart;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.button.ButtonStyle;
import org.jebtk.modern.button.ModernClickWidget;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.VBoxAutoWidth;
import org.jebtk.modern.ribbon.RibbonButton;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Sample;

public class CartPanel extends ModernComponent implements ChangeListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ModernClickWidget mClearButton = new RibbonButton("Clear Cart").setButtonStyle(ButtonStyle.PILL_CONTENT);

  private ModernWindow mParent;

  public CartPanel(ModernWindow parent) {
    mParent = parent;

    Box box = new HBox();
    box.add(Box.createHorizontalGlue());
    box.add(mClearButton);
    box.setBorder(TOP_BOTTOM_BORDER);

    setHeader(box);

    SampleCartService.getInstance().addChangeListener(this);

    mClearButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        clear();
      }
    });
  }

  private void clear() {
    ModernDialogStatus status = ModernMessageDialog.createOkCancelWarningDialog(
        mParent,
        "Are you sure you want to empty the cart?");

    if (status == ModernDialogStatus.OK) {
      SampleCartService.getInstance().clear();
    }
  }

  @Override
  public void changed(ChangeEvent e) {
    VBoxAutoWidth box = new VBoxAutoWidth();

    int c = 1;

    for (Sample s : SampleCartService.getInstance()) {
      box.add(new CartEntry(c++, s));
    }
    
    box.add(Box.createVerticalGlue());

    setBody(new ModernComponent(new ModernScrollPane(box)
        .setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER), QUAD_PADDING));
  }

}
