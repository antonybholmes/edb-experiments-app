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
package edu.columbia.rdf.edb.experiments.app.modules.chipseq;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.Mathematics;
import org.jebtk.core.io.Io;
import org.jebtk.core.io.Temp;
import org.jebtk.core.text.TextUtils;
import org.jebtk.graphplot.PlotFactory;
import org.jebtk.graphplot.figure.SubFigure;
import org.jebtk.graphplot.figure.SubFigurePanel;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.scrollpane.ModernScrollPane;

import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.DownloadManager;

/**
 * The Class FastqcPanel.
 */
public class FastqcPanel extends ModernComponent {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new fastqc panel.
   *
   * @param sample the sample
   * @param file the file
   */
  public FastqcPanel(Sample sample, FileDescriptor file) {
    try {
      java.nio.file.Path tempFile = Temp.generateTempFile();

      DownloadManager.download(file, tempFile);

      List<String> lines = Io.getLines(tempFile);

      // System.err.println(lines.toString());

      makePerBaseQualityPlot(lines);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Make per base quality plot.
   *
   * @param lines the lines
   * @throws ParseException the parse exception
   */
  private void makePerBaseQualityPlot(List<String> lines)
      throws ParseException {

    List<String> qcLines = new ArrayList<String>();

    boolean found = false;

    for (String line : lines) {
      if (line.contains("Per base sequence quality")) {
        found = true;
      } else {
        if (found) {
          if (line.startsWith(">>")) {
            break;
          }

          qcLines.add(line);
        }
      }
    }

    DataFrame m = DataFrame.createNumericalMatrix(5, qcLines.size() - 1);

    XYSeriesGroup g = new XYSeriesGroup();

    for (int i = 1; i < qcLines.size(); ++i) {
      System.err.println(qcLines.get(i));

      List<String> tokens = TextUtils.tabSplit(qcLines.get(i));

      m.setColumnName(i - 1, tokens.get(0));

      m.set(0, i - 1, TextUtils.parseDouble(tokens.get(2)));
      m.set(1, i - 1, TextUtils.parseDouble(tokens.get(3)));
      m.set(2, i - 1, TextUtils.parseDouble(tokens.get(4)));
      m.set(3, i - 1, TextUtils.parseDouble(tokens.get(5)));
      m.set(4, i - 1, TextUtils.parseDouble(tokens.get(6)));

      g.add(new XYSeries(tokens.get(0)));
    }

    SubFigure subFigure = new SubFigure();

    PlotFactory.createBoxWhiskerSummaryPlot(m, subFigure.currentAxes(), g);

    subFigure.currentAxes().setInternalSize(g.getCount() * 24, 300);
    subFigure.currentAxes().getX1Axis().getTicks().getMajorTicks()
        .setRotation(Mathematics.HALF_PI);

    ModernScrollPane scrollPane = new ModernScrollPane(
        new SubFigurePanel(subFigure));

    setBody(scrollPane);
  }
}
