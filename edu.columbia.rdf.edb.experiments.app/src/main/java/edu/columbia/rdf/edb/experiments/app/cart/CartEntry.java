package edu.columbia.rdf.edb.experiments.app.cart;

import org.jebtk.modern.AssetService;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.CrossVectorIcon;
import org.jebtk.modern.ribbon.RibbonButton;
import org.jebtk.modern.text.ModernLabel;
import org.jebtk.modern.widget.ButtonStyle;
import org.jebtk.modern.widget.ModernClickWidget;

import edu.columbia.rdf.edb.Sample;

public class CartEntry extends ModernComponent {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ModernClickWidget mClearButton = new RibbonButton(
      AssetService.getInstance().loadIcon(CrossVectorIcon.class, 16)).setButtonStyle(ButtonStyle.CIRCLE);

  private Sample mSample;

  public CartEntry(int index, Sample sample) {
    mSample = sample;

    add(new ModernLabel(index + ". " + sample.getName()));

    setRight(mClearButton);

    setBorder(PADDING);

    mClearButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        SampleCartService.getInstance().remove(mSample);
      }
    });
  }
}
