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
package com.helger.pdflayout.config;

import java.awt.Color;

import javax.annotation.Nonnull;

import com.helger.pdflayout.spec.BorderSpec;
import com.helger.pdflayout.spec.BorderStyleSpec;
import com.helger.pdflayout.spec.FontSpec;
import com.helger.pdflayout.spec.IPreloadFontResolver;
import com.helger.pdflayout.spec.LineDashPatternSpec;
import com.helger.pdflayout.spec.MarginSpec;
import com.helger.pdflayout.spec.PaddingSpec;
import com.helger.pdflayout.spec.SizeSpec;
import com.helger.pdflayout.spec.TextAndWidthSpec;
import com.helger.pdflayout.spec.WidthSpec;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistry;

/**
 * Micro type converter registration for all micro type converter contained in
 * this project. Must be called manually, because an
 * {@link IPreloadFontResolver} is required!
 *
 * @author Philip Helger
 */
public final class PDFMicroTypeConverterRegistry
{
  private PDFMicroTypeConverterRegistry ()
  {}

  public static void registerMicroTypeConverter (@Nonnull final IMicroTypeConverterRegistry aRegistry,
                                                 @Nonnull final IPreloadFontResolver aPreloadFontResolver)
  {
    // Details
    aRegistry.registerMicroElementTypeConverter (BorderSpec.class, new BorderSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BorderStyleSpec.class, new BorderStyleSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (Color.class, new ColorMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (FontSpec.class, new FontSpecMicroTypeConverter (aPreloadFontResolver));
    aRegistry.registerMicroElementTypeConverter (LineDashPatternSpec.class,
                                                 new LineDashPatternSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (MarginSpec.class, new MarginSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PaddingSpec.class, new PaddingSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SizeSpec.class, new SizeSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (TextAndWidthSpec.class, new TextAndWidthSpecMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (WidthSpec.class, new WidthSpecMicroTypeConverter ());
  }
}