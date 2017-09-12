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
package com.helger.pdflayout.render;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.ToStringGenerator;

/**
 * The current context for preparing an element. The preparation context depends
 * on the location of an element.
 *
 * @author Philip Helger
 */
@Immutable
public final class PreparationContext
{
  private final PreparationContextGlobal m_aGlobalCtx;
  private final float m_fAvailableWidth;
  private final float m_fAvailableHeight;

  /**
   * Constructor
   *
   * @param aGlobalCtx
   *        The global preparation context worked upon
   * @param fAvailableWidth
   *        The available width for an element, without the element's margin and
   *        padding. Should be &gt; 0.
   * @param fAvailableHeight
   *        The available height for an element, without the element's margin
   *        and padding. Should be &gt; 0.
   */
  public PreparationContext (@Nonnull final PreparationContextGlobal aGlobalCtx,
                             @Nonnegative final float fAvailableWidth,
                             @Nonnegative final float fAvailableHeight)
  {
    m_aGlobalCtx = ValueEnforcer.notNull (aGlobalCtx, "GlobalCtx");
    m_fAvailableWidth = ValueEnforcer.isGE0 (fAvailableWidth, "AvailableWidth");
    m_fAvailableHeight = ValueEnforcer.isGE0 (fAvailableHeight, "AvailableHeight");
  }

  @Nonnull
  public PreparationContextGlobal getGlobalContext ()
  {
    return m_aGlobalCtx;
  }

  /**
   * @return The available width for an element, without the element's margin
   *         and padding. Should be &gt; 0.
   */
  public float getAvailableWidth ()
  {
    return m_fAvailableWidth;
  }

  /**
   * @return The available height for an element, without the element's margin
   *         and padding. Should be &gt; 0.
   */
  public float getAvailableHeight ()
  {
    return m_fAvailableHeight;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("availableWidth", m_fAvailableWidth)
                                       .append ("availableHeight", m_fAvailableHeight)
                                       .getToString ();
  }
}
