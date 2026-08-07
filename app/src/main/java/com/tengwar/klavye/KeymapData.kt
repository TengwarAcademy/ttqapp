// OTOMATİK ÜRETİLDİ — TTQ-Klavye.txt dosyasındaki tuş haritasından dönüştürüldü.
// Elle düzenlemeyin; kaynağı değiştirip yeniden üretin.
package com.tengwar.klavye

data class KeyVariant(
    val glyph: String?,
    val label: String,
    val desc: Boolean = false,
    val labelPos: String? = null
)

data class KeyCell(
    val standard: KeyVariant,
    val shift: KeyVariant,
    val altgr: KeyVariant,
    val shiftAltgr: KeyVariant
)

object KeymapData {
    val rowA: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("«", "Tırnak", false, null),
                shift = KeyVariant("º", "", false, null),
                altgr = KeyVariant("P", "Çiftleme 1", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ñ", "1", false, null),
                shift = KeyVariant("ñT", "", false, null),
                altgr = KeyVariant("ñ·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ò", "2", false, null),
                shift = KeyVariant("ò%", "", false, null),
                altgr = KeyVariant("ò·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ó", "3", false, null),
                shift = KeyVariant("ó%", "", false, null),
                altgr = KeyVariant("ó·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ô", "4", false, null),
                shift = KeyVariant("ôG", "", false, null),
                altgr = KeyVariant("ô·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("õ", "5", false, null),
                shift = KeyVariant("õT", "", false, null),
                altgr = KeyVariant("õ·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ö", "6", false, null),
                shift = KeyVariant("ö%", "", false, null),
                altgr = KeyVariant("ö·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("÷", "7", false, null),
                shift = KeyVariant("÷T", "", false, null),
                altgr = KeyVariant("÷·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ø", "8", false, null),
                shift = KeyVariant("øT", "", false, null),
                altgr = KeyVariant("ø·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ù", "9", false, null),
                shift = KeyVariant("ù%", "", false, null),
                altgr = KeyVariant("ù·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ð", "0", false, null),
                shift = KeyVariant("ðG", "", false, null),
                altgr = KeyVariant("ð·", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant(null, "", false, null),
                shift = KeyVariant("À", "Soru İşareti", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("Ì", "", false, null),
                shift = KeyVariant("Í", "", false, null),
                altgr = KeyVariant("Î", "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
    )
    val rowB: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("`", "", false, null),
                shift = KeyVariant("~", "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("Ø", "Ě", true, "bottom"),
                shift = KeyVariant("Ù", "", false, null),
                altgr = KeyVariant("Ú", "", false, null),
                shiftAltgr = KeyVariant("Û", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("$", "E", true, "bottom"),
                shift = KeyVariant("R", "", false, null),
                altgr = KeyVariant("F", "", false, null),
                shiftAltgr = KeyVariant("V", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("7", "R", false, null),
                shift = KeyVariant("u", "R*", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("1", "T", false, null),
                shift = KeyVariant("!", "Gen. T", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("l", "Y", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("&", "U", false, null),
                shift = KeyVariant("U", "", false, null),
                altgr = KeyVariant("J", "", false, null),
                shiftAltgr = KeyVariant("M", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("%", "I", false, null),
                shift = KeyVariant("T", "", false, null),
                altgr = KeyVariant("G", "", false, null),
                shiftAltgr = KeyVariant("B", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("^", "O", false, null),
                shift = KeyVariant("Y", "", false, null),
                altgr = KeyVariant("H", "", false, null),
                shiftAltgr = KeyVariant("N", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("q", "P", false, null),
                shift = KeyVariant("Q", "Gen. P", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("b", "Ğ", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("Ô", "Ü", false, null),
                shift = KeyVariant("Õ", "", false, null),
                altgr = KeyVariant("Ö", "", false, null),
                shiftAltgr = KeyVariant("×", "", false, null)
            ),
    )
    val rowC: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("#", "A", false, null),
                shift = KeyVariant("E", "", false, null),
                altgr = KeyVariant("D", "", false, null),
                shiftAltgr = KeyVariant("C", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("8", "S", false, null),
                shift = KeyVariant("i", "S (Ters)", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("2", "D", false, null),
                shift = KeyVariant("@", "Gen. D", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("e", "F", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("x", "G", false, null),
                shift = KeyVariant("X", "Gen. G", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("9", "H", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("f", "J", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("z", "K", false, null),
                shift = KeyVariant("Z", "Gen. K", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("j", "L", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("d", "Ş", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("È%", "İ", false, null),
                shift = KeyVariant("ÉT", "", false, null),
                altgr = KeyVariant("ÊG", "", false, null),
                shiftAltgr = KeyVariant("BË", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("¹", "Virgül", false, null),
                shift = KeyVariant("Ã", "Noktalı Virgül", false, null),
                altgr = KeyVariant("ˆ", "Üç Nokta", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
    )
    val rowD: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("ü", "", true, null),
                shift = KeyVariant("ý", "", false, null),
                altgr = KeyVariant("þ", "", false, null),
                shiftAltgr = KeyVariant("ÿ", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("k", "Z", false, null),
                shift = KeyVariant("<", "Z*", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("K", "Z*", false, null),
                shift = KeyVariant(",", "Z* (Ters)", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("s", "C", false, null),
                shift = KeyVariant("S", "Gen. C", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("r", "V", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("w", "B", false, null),
                shift = KeyVariant("W", "Gen. B", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("5", "N", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("t", "M", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("^È", "Ö", false, null),
                shift = KeyVariant("YÉ", "", false, null),
                altgr = KeyVariant("HÊ", "", false, null),
                shiftAltgr = KeyVariant("NË", "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("a", "Ç", false, null),
                shift = KeyVariant("A", "Gen. Ç", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("·", "Nokta", false, null),
                shift = KeyVariant("-", "İki Nokta", false, null),
                altgr = KeyVariant("Ë", "Nokta", false, null),
                shiftAltgr = KeyVariant("Ê", "Nokta", false, null)
            ),
    )
}