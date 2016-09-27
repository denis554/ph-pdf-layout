package com.helger.pdflayout.element.table;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;
import com.helger.pdflayout.spec.BorderStyleSpec;

/**
 * Default table grids.
 *
 * @author Philip Helger
 */
public enum EPLTableGridType implements IPLTableGridType
{
  /** Create no grid lines at all */
  NONE ("none")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        aRow.forEachCell ( (aCell, nCellIndex) -> {
          aCell.setBorder (null, null, null, null);
        });
      });
    }
  },
  /**
   * Create all grid lines. The first row has the border also on top, the other
   * rows don't. The first column also has a border on the left, the others
   * don't.
   */
  FULL ("full")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        if (nRowIndex == 0)
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            if (nCellIndex == 0)
              aCell.setBorder (aBSS, aBSS, aBSS, aBSS);
            else
              aCell.setBorder (aBSS, aBSS, aBSS, null);
          });
        else
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            if (nCellIndex == 0)
              aCell.setBorder (null, aBSS, aBSS, aBSS);
            else
              aCell.setBorder (null, aBSS, aBSS, null);
          });
      });
    }
  },
  /**
   * Create all grid lines except for the border lines. The first row has the
   * border also on top, the other rows don't. The
   */
  FULL_NO_BORDER ("full_no_border")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      final int nLastRowIndex = aTable.getRowCount () - 1;
      final int nLastCellIndex = aTable.getColumnCount () - 1;
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        if (nRowIndex == nLastRowIndex)
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            if (nCellIndex == nLastCellIndex)
              aCell.setBorder (null, null, null, null);
            else
              aCell.setBorder (null, aBSS, null, null);
          });
        else
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            if (nCellIndex == nLastCellIndex)
              aCell.setBorder (null, null, aBSS, null);
            else
              aCell.setBorder (null, aBSS, aBSS, null);
          });
      });
    }
  },
  /**
   * Create all horizontal lines. The first row has a border on top and bottom,
   * all other rows only at the bottom
   */
  HORZ_ALL ("horz_all")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        if (nRowIndex == 0)
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            aCell.setBorder (aBSS, null, aBSS, null);
          });
        else
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            aCell.setBorder (null, null, aBSS, null);
          });
      });
    }
  },
  /**
   * Create only horizontal lines but without the border lines on top and on
   * bottom. All rows have a border on bottom except for the last line which has
   * no border.
   */
  HORZ_NO_BORDER ("horz_no_border")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      final int nLastRowIndex = aTable.getRowCount () - 1;
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        if (nRowIndex == nLastRowIndex)
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            aCell.setBorder (null, null, null, null);
          });
        else
          aRow.forEachCell ( (aCell, nCellIndex) -> {
            aCell.setBorder (null, null, aBSS, null);
          });
      });
    }
  },
  /**
   * Create all vertical lines. The first column has a border on left and right,
   * all other columns only at the right
   */
  VERT_ALL ("vert_all")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        aRow.forEachCell ( (aCell, nCellIndex) -> {
          if (nCellIndex == 0)
            aCell.setBorder (null, aBSS, null, aBSS);
          else
            aCell.setBorder (null, aBSS, null, null);
        });
      });
    }
  },
  /**
   * Create only vertical lines but without the border lines left and right. The
   * first column has a border on left and right, all other columns only at the
   * right
   */
  VERT_NO_BORDER ("vert_no_border")
  {
    @Override
    public void applyGridToTable (@Nonnull final PLTable aTable, @Nonnull final BorderStyleSpec aBSS)
    {
      ValueEnforcer.notNull (aTable, "Table");
      ValueEnforcer.notNull (aBSS, "BorderStyleSpec");
      final int nLastCellIndex = aTable.getColumnCount () - 1;
      aTable.forEachRow ( (aRow, nRowIndex) -> {
        aRow.forEachCell ( (aCell, nCellIndex) -> {
          if (nCellIndex == nLastCellIndex)
            aCell.setBorder (null, null, null, null);
          else
            aCell.setBorder (null, aBSS, null, null);
        });
      });
    }
  };

  private final String m_sID;

  private EPLTableGridType (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public static EPLTableGridType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPLTableGridType.class, sID);
  }
}
