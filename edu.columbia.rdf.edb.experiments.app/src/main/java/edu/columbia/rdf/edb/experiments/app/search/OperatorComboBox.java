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
package edu.columbia.rdf.edb.experiments.app.search;

import org.jebtk.core.search.SearchStackOperator;
import org.jebtk.modern.combobox.AndOrLogicalComboBox;

/**
 * Extension of the Logical Combo box to support MatchStackOperators which are a
 * superset of the boolean operators.
 * 
 * @author Antony Holmes Holmes
 */
public class OperatorComboBox extends AndOrLogicalComboBox {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new operator combo box.
   *
   * @param operator the operator
   */
  public OperatorComboBox(SearchStackOperator operator) {
    this.setSelectedIndex(getIndexFromType(operator));
  }

  /**
   * Gets the operator type from index.
   *
   * @param index the index
   * @return the operator type from index
   */
  public static final SearchStackOperator getOperatorTypeFromIndex(int index) {
    switch (index) {
    case 0:
      return SearchStackOperator.AND;
    case 1:
      return SearchStackOperator.OR;
    case 2:
      return SearchStackOperator.NOR;
    case 3:
      return SearchStackOperator.XOR;
    case 4:
      return SearchStackOperator.NAND;
    default:
      return SearchStackOperator.INVALID;
    }
  }

  /**
   * Gets the index from type.
   *
   * @param type the type
   * @return the index from type
   */
  public static final int getIndexFromType(SearchStackOperator type) {
    switch (type) {
    case AND:
      return 0;
    case OR:
      return 1;
    case NOR:
      return 2;
    case XOR:
      return 3;
    case NAND:
      return 4;
    default:
      return -1;
    }
  }
}
