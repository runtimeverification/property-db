/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util.spi;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle.Control;

/** {@collect.stats}
 *      
* {@description.open}
     * An abstract class for service providers that
 * provide localized currency symbols and display names for the
 * {@link java.util.Currency Currency} class.
 * Note that currency symbols are considered names when determining
 * behaviors described in the
 * {@link java.util.spi.LocaleServiceProvider LocaleServiceProvider}
 * specification.

     * {@description.close} *
 * @since        1.6
 */
public abstract class CurrencyNameProvider extends LocaleServiceProvider {

    /** {@collect.stats}
     *      
* {@description.open}
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)

     * {@description.close}     */
    protected CurrencyNameProvider() {
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Gets the symbol of the given currency code for the specified locale.
     * For example, for "USD" (US Dollar), the symbol is "$" if the specified
     * locale is the US, while for other locales it may be "US$". If no
     * symbol can be determined, null should be returned.

     * {@description.close}     *
     * @param currencyCode the ISO 4217 currency code, which
     *     consists of three upper-case letters between 'A' (U+0041) and
     *     'Z' (U+005A)
     * @param locale the desired locale
     * @return the symbol of the given currency code for the specified locale, or null if
     *     the symbol is not available for the locale
     * @exception NullPointerException if <code>currencyCode</code> or
     *     <code>locale</code> is null
     * @exception IllegalArgumentException if <code>currencyCode</code> is not in
     *     the form of three upper-case letters, or <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.util.Currency#getSymbol(java.util.Locale)
     */
    public abstract String getSymbol(String currencyCode, Locale locale);

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns a name for the currency that is appropriate for display to the
     * user.  The default implementation returns null.

     * {@description.close}     *
     * @param currencyCode the ISO 4217 currency code, which
     *     consists of three upper-case letters between 'A' (U+0041) and
     *     'Z' (U+005A)
     * @param locale the desired locale
     * @return the name for the currency that is appropriate for display to the
     *     user, or null if the name is not available for the locale
     * @exception IllegalArgumentException if <code>currencyCode</code> is not in
     *     the form of three upper-case letters, or <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @exception NullPointerException if <code>currencyCode</code> or
     *     <code>locale</code> is <code>null</code>
     * @since 1.7
     */
    public String getDisplayName(String currencyCode, Locale locale) {
        if (currencyCode == null || locale == null) {
            throw new NullPointerException();
        }

        // Check whether the currencyCode is valid
        char[] charray = currencyCode.toCharArray();
        if (charray.length != 3) {
            throw new IllegalArgumentException("The currencyCode is not in the form of three upper-case letters.");
        }
        for (char c : charray) {
            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("The currencyCode is not in the form of three upper-case letters.");
            }
        }

        // Check whether the locale is valid
        Control c = Control.getNoFallbackControl(Control.FORMAT_DEFAULT);
        for (Locale l : getAvailableLocales()) {
            if (c.getCandidateLocales("", l).contains(locale)) {
                return null;
            }
        }

        throw new IllegalArgumentException("The locale is not available");
    }
}
