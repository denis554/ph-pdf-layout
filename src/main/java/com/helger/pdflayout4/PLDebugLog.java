/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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
package com.helger.pdflayout4;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.pdflayout4.base.IPLHasMarginBorderPadding;
import com.helger.pdflayout4.base.IPLObject;
import com.helger.pdflayout4.spec.SizeSpec;

/**
 * This class allows for some debug logging on PDF creation.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class PLDebugLog
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PLDebugLog.class);
  private static boolean s_bDebugText = false;
  private static boolean s_bDebugFont = false;
  private static boolean s_bDebugSplit = false;
  private static boolean s_bDebugPrepare = false;
  private static boolean s_bDebugRender = false;

  private PLDebugLog ()
  {}

  public static boolean isDebugText ()
  {
    return s_bDebugText;
  }

  public static void setDebugText (final boolean bDebugText)
  {
    s_bDebugText = bDebugText;
  }

  public static void debugText (@Nonnull final IPLObject <?> aElement, final String sMsg)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("[Text] " + aElement.getDebugID () + " " + sMsg);
  }

  public static boolean isDebugFont ()
  {
    return s_bDebugFont;
  }

  public static void setDebugFont (final boolean bDebugFont)
  {
    s_bDebugFont = bDebugFont;
  }

  public static void debugFont (@Nonnull final String sFontID, final String sMsg)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("[Font] " + sFontID + " " + sMsg);
  }

  public static boolean isDebugSplit ()
  {
    return s_bDebugSplit;
  }

  public static void setDebugSplit (final boolean bDebugSplit)
  {
    s_bDebugSplit = bDebugSplit;
  }

  public static void debugSplit (@Nonnull final IPLObject <?> aElement, final String sMsg)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("[Splitting] " + aElement.getDebugID () + " " + sMsg);
  }

  public static boolean isDebugPrepare ()
  {
    return s_bDebugPrepare;
  }

  public static void setDebugPrepare (final boolean bDebugPrepare)
  {
    s_bDebugPrepare = bDebugPrepare;
  }

  public static void debugPrepare (@Nonnull final IPLObject <?> aElement, final String sMsg)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("[Preparing] " + aElement.getDebugID () + " " + sMsg);
  }

  public static boolean isDebugRender ()
  {
    return s_bDebugRender;
  }

  public static void setDebugRender (final boolean bDebugRender)
  {
    s_bDebugRender = bDebugRender;
  }

  public static void debugRender (@Nonnull final IPLObject <?> aElement, final String sMsg)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("[Rendering] " + aElement.getDebugID () + " " + sMsg);
  }

  /**
   * Shortcut to globally en- or disable debugging
   *
   * @param bDebug
   *        <code>true</code> to enable debug output
   */
  public static void setDebugAll (final boolean bDebug)
  {
    setDebugText (bDebug);
    setDebugFont (bDebug);
    setDebugSplit (bDebug);
    setDebugPrepare (bDebug);
    setDebugRender (bDebug);
  }

  @Nonnull
  public static String getXY (final float fX, final float fY)
  {
    return "[" + fX + "/" + fY + "]";
  }

  @Nonnull
  public static String getWH (@Nonnull final SizeSpec aSize)
  {
    return getWH (aSize.getWidth (), aSize.getHeight ());
  }

  @Nonnull
  public static String getWH (final float fWidth, final float fHeight)
  {
    return fWidth + "/" + fHeight;
  }

  @Nonnull
  public static String getXYWH (final float fLeft, final float fTop, final float fWidth, final float fHeight)
  {
    final float fRight = fLeft + fWidth;
    final float fBottom = fTop - fHeight;
    return "LB" + getXY (fLeft, fBottom) + " - RT" + getXY (fRight, fTop) + " (=WH " + getWH (fWidth, fHeight) + ")";
  }

  @Nonnull
  public static String getXMBP (@Nonnull final IPLHasMarginBorderPadding <?> aElement)
  {
    return "[X-MBP: " +
           aElement.getMarginXSum () +
           "/" +
           aElement.getBorderXSumWidth () +
           "/" +
           aElement.getPaddingXSum () +
           "=" +
           aElement.getOutlineXSum () +
           "]";
  }

  @Nonnull
  public static String getYMBP (@Nonnull final IPLHasMarginBorderPadding <?> aElement)
  {
    return "[Y-MBP: " +
           aElement.getMarginYSum () +
           "/" +
           aElement.getBorderYSumWidth () +
           "/" +
           aElement.getPaddingYSum () +
           "=" +
           aElement.getOutlineYSum () +
           "]";
  }
}
