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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

// TODO: Auto-generated Javadoc
/**
 * The Class VerticalCardLayout.
 */
public class VerticalCardLayout implements LayoutManager {

  /** The m preferred height. */
  private int mPreferredHeight = 0;

  /** The size unknown. */
  private boolean sizeUnknown = true;

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
   * java.awt.Component)
   */
  /* Required by LayoutManager. */
  @Override
  public void addLayoutComponent(String name, Component comp) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  /* Required by LayoutManager. */
  @Override
  public void removeLayoutComponent(Component comp) {
  }

  /**
   * Sets the sizes.
   *
   * @param parent the new sizes
   */
  private void setSizes(Container parent) {
    int nComps = parent.getComponentCount();

    Dimension d = null;

    // Reset preferred/minimum width and height.
    mPreferredHeight = 0;

    for (int i = 0; i < nComps; i++) {
      Component c = parent.getComponent(i);

      if (c.isVisible()) {
        d = c.getPreferredSize();

        mPreferredHeight += d.height;

      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  /* Required by LayoutManager. */
  @Override
  public Dimension preferredLayoutSize(Container parent) {
    Dimension dim = new Dimension(parent.getWidth(), 0);

    setSizes(parent);

    // Always add the container's insets!
    Insets insets = parent.getInsets();

    dim.height = mPreferredHeight + insets.top + insets.bottom;

    sizeUnknown = false;

    return dim;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  /* Required by LayoutManager. */
  @Override
  public Dimension minimumLayoutSize(Container parent) {
    return preferredLayoutSize(parent);
  }

  /* Required by LayoutManager. */
  /*
   * (non-Javadoc)
   * 
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  /*
   * This is called when the panel is first displayed, and every time its size
   * changes. Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize
   * will be called -- in the case of applets, at least, they probably won't be.
   */
  @Override
  public void layoutContainer(Container parent) {
    Insets insets = parent.getInsets();

    int maxWidth = parent.getWidth() - (insets.left + insets.right);

    int nComps = parent.getComponentCount();

    int x = insets.left;
    int y = insets.top;

    // Go through the components' sizes, if neither
    // preferredLayoutSize nor minimumLayoutSize has
    // been called.
    if (sizeUnknown) {
      setSizes(parent);
    }

    for (int i = 0; i < nComps; i++) {
      Component c = parent.getComponent(i);

      if (c.isVisible()) {
        Dimension d = c.getPreferredSize();

        // Set the component's size and position.
        c.setBounds(x, y, maxWidth, d.height);

        y += d.height;
      }
    }
  }
}