/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.pdflayout.element.table;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.string.ToStringGenerator;
import com.helger.pdflayout.base.AbstractPLElement;
import com.helger.pdflayout.base.IPLRenderableObject;
import com.helger.pdflayout.element.hbox.AbstractPLHBox;
import com.helger.pdflayout.element.hbox.PLHBox;
import com.helger.pdflayout.element.hbox.PLHBoxColumn;
import com.helger.pdflayout.element.special.PLSpacerX;
import com.helger.pdflayout.element.vbox.AbstractPLVBox;
import com.helger.pdflayout.element.vbox.PLVBoxRow;
import com.helger.pdflayout.spec.EValueUOMType;
import com.helger.pdflayout.spec.WidthSpec;

/**
 * A special table with a repeating header
 *
 * @author Philip Helger
 */
public class PLTable extends AbstractPLVBox <PLTable>
{
  private final ICommonsList <WidthSpec> m_aWidths;

  /**
   * @param aWidths
   *        Must all be of the same type!
   */
  public PLTable (@Nonnull @Nonempty final Iterable <? extends WidthSpec> aWidths)
  {
    ValueEnforcer.notEmptyNoNullValue (aWidths, "Widths");
    setVertSplittable (true);

    // Check that all width are of the same type
    EValueUOMType eWidthType = null;
    for (final WidthSpec aWidth : aWidths)
      if (eWidthType == null)
        eWidthType = aWidth.getType ();
      else
        if (aWidth.getType () != eWidthType)
          throw new IllegalArgumentException ("All widths must be of the same type! Found " +
                                              eWidthType +
                                              " and " +
                                              aWidth.getType ());
    m_aWidths = new CommonsArrayList<> (aWidths);
  }

  @Nonnull
  @OverridingMethodsMustInvokeSuper
  public PLTable setBasicDataFrom (@Nonnull final PLTable aSource)
  {
    super.setBasicDataFrom (aSource);
    return this;
  }

  /**
   * @return A copy of the list with all widths as specified in the constructor.
   *         Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <WidthSpec> getAllWidths ()
  {
    return m_aWidths.getClone ();
  }

  /**
   * @return The number of columns in the table. Always &ge; 0.
   */
  @Nonnegative
  public int getColumnCount ()
  {
    return m_aWidths.size ();
  }

  /**
   * Add a new table row. All contained elements are added with the specified
   * width in the constructor. <code>null</code> elements are represented as
   * empty cells.
   *
   * @param aElements
   *        The elements to add. May be <code>null</code>.
   * @return The added row and never <code>null</code>.
   */
  @Nonnull
  public PLHBox addTableRow (@Nullable final AbstractPLElement <?>... aElements)
  {
    return addTableRow (new CommonsArrayList<> (aElements));
  }

  /**
   * Add a new table row. All contained elements are added with the specified
   * width in the constructor. <code>null</code> elements are represented as
   * empty cells.
   *
   * @param aElements
   *        The elements to add. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public PLHBox addTableRow (@Nonnull final Collection <? extends IPLRenderableObject <?>> aElements)
  {
    ValueEnforcer.notNull (aElements, "Elements");
    if (aElements.size () > m_aWidths.size ())
      throw new IllegalArgumentException ("More elements in row (" +
                                          aElements.size () +
                                          ") than defined in the table (" +
                                          m_aWidths.size () +
                                          ")!");

    final PLHBox aRowHBox = new PLHBox ().setVertSplittable (true);
    int nWidthIndex = 0;
    for (IPLRenderableObject <?> aElement : aElements)
    {
      if (aElement == null)
      {
        // null elements end as a spacer
        aElement = new PLSpacerX ();
      }
      final WidthSpec aWidth = m_aWidths.get (nWidthIndex);
      aRowHBox.addColumn (aElement, aWidth);
      ++nWidthIndex;
    }
    super.addRow (aRowHBox);
    return aRowHBox;
  }

  @Nonnull
  public PLHBox addTableRowExt (@Nonnull final PLTableCell... aCells)
  {
    return addTableRowExt (new CommonsArrayList<> (aCells));
  }

  /**
   * Add a new table row. All contained elements are added with the specified
   * width in the constructor. <code>null</code> elements are represented as
   * empty cells.
   *
   * @param aCells
   *        The cells to add. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public PLHBox addTableRowExt (@Nonnull final Iterable <? extends PLTableCell> aCells)
  {
    ValueEnforcer.notNull (aCells, "Cells");

    // Small consistency check
    int nUsedCols = 0;
    for (final PLTableCell aCell : aCells)
      nUsedCols += aCell.getColSpan ();
    if (nUsedCols > m_aWidths.size ())
      throw new IllegalArgumentException ("More cells in row (" +
                                          nUsedCols +
                                          ") than defined in the table (" +
                                          m_aWidths.size () +
                                          ")!");

    final PLHBox aHBox = new PLHBox ().setVertSplittable (true);
    int nWidthIndex = 0;
    for (final PLTableCell aCell : aCells)
    {
      final int nCols = aCell.getColSpan ();
      if (nCols == 1)
      {
        aHBox.addAndReturnColumn (aCell.getElement (), m_aWidths.get (nWidthIndex));
      }
      else
      {
        final List <WidthSpec> aWidths = m_aWidths.subList (nWidthIndex, nWidthIndex + nCols);
        final EValueUOMType eWidthType = aWidths.get (0).getType ();
        WidthSpec aRealWidth;
        if (eWidthType == EValueUOMType.STAR)
        {
          // aggregate
          aRealWidth = WidthSpec.perc (nCols * 100f / m_aWidths.size ());
        }
        else
        {
          // aggregate values
          float fWidth = 0;
          for (final WidthSpec aWidth : aWidths)
            fWidth += aWidth.getValue ();
          aRealWidth = new WidthSpec (eWidthType, fWidth);
        }
        aHBox.addAndReturnColumn (aCell.getElement (), aRealWidth);
      }
      nWidthIndex += nCols;
    }
    super.addRow (aHBox);
    return aHBox;
  }

  /**
   * Get the cell at the specified row and column index
   *
   * @param nRowIndex
   *        row index
   * @param nColumnIndex
   *        column index
   * @return <code>null</code> if row and/or column index are out of bounds.
   * @since 3.0.4
   */
  @Nullable
  public IPLRenderableObject <?> getCellElement (@Nonnegative final int nRowIndex, @Nonnegative final int nColumnIndex)
  {
    final PLVBoxRow aRow = getRowAtIndex (nRowIndex);
    if (aRow != null)
    {
      final PLHBoxColumn aColumn = ((AbstractPLHBox <?>) aRow.getElement ()).getColumnAtIndex (nColumnIndex);
      if (aColumn != null)
        return aColumn.getElement ();
    }
    return null;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("Width", m_aWidths).toString ();
  }

  /**
   * Create a new table with the specified percentages.
   *
   * @param aPercentages
   *        The array to use. The sum of all percentages should be &le; 100. May
   *        neither be <code>null</code> nor empty.
   * @return The created {@link PLTable} and never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static PLTable createWithPercentage (@Nonnull @Nonempty final float... aPercentages)
  {
    ValueEnforcer.notEmpty (aPercentages, "Percentages");

    final ICommonsList <WidthSpec> aWidths = new CommonsArrayList<> (aPercentages.length);
    for (final float fPercentage : aPercentages)
      aWidths.add (WidthSpec.perc (fPercentage));
    return new PLTable (aWidths);
  }

  /**
   * Create a new table with evenly sized columns.
   *
   * @param nColumnCount
   *        The number of columns to use. Must be &gt; 0.
   * @return The created {@link PLTable} and never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static PLTable createWithEvenlySizedColumns (@Nonnegative final int nColumnCount)
  {
    ValueEnforcer.isGT0 (nColumnCount, "ColumnCount");

    final ICommonsList <WidthSpec> aWidths = new CommonsArrayList<> (nColumnCount);
    for (int i = 0; i < nColumnCount; ++i)
      aWidths.add (WidthSpec.star ());
    return new PLTable (aWidths);
  }
}
