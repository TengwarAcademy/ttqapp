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
                shift = KeyVariant("º", "Ára (nokta)", false, null),
                altgr = KeyVariant("P", "Çiftleme 1", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ñ", "1", false, null),
                shift = KeyVariant("Á", "Ünlem İşareti", false, null),
                altgr = KeyVariant(":", "Çiftleme 2", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ò", "2", false, null),
                shift = KeyVariant("²", "Kesme İşareti", false, null),
                altgr = KeyVariant("p", "Çiftleme 3", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ó", "3", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant("°", "Çiftleme 4", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ô", "4", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(";", "Çiftleme 5", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("õ", "5", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ö", "6", false, null),
                shift = KeyVariant(null, "", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("÷", "7", false, null),
                shift = KeyVariant("›", "Alt. Parantez", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ø", "8", false, null),
                shift = KeyVariant("Œ", "Parantez Aç", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ù", "9", false, null),
                shift = KeyVariant("œ", "Parantez Kapa", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("ð", "0", false, null),
                shift = KeyVariant("¬", "Eşittir", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant(null, "", false, null),
                shift = KeyVariant("À", "Soru İşareti", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("Â", "Tire", false, null),
                shift = KeyVariant("Ì", "Yumuşatma", false, null),
                altgr = KeyVariant("Í", "Yumuşatma", false, null),
                shiftAltgr = KeyVariant("Î", "Yumuşatma", false, null)
            ),
    )
    val rowB: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("`", "Telco", false, null),
                shift = KeyVariant("~", "Ára", false, null),
                altgr = KeyVariant(null, "", false, null),
                shiftAltgr = KeyVariant(null, "", false, null)
            ),
        KeyCell(
                standard = KeyVariant("Ø", "E (açık)", true, "bottom"),
                shift = KeyVariant("Ø", "E (açık)", true, "bottom"),
                altgr = KeyVariant("Ø", "E (açık)", true, "bottom"),
                shiftAltgr = KeyVariant("Ø", "E (açık)", true, "bottom")
            ),
        KeyCell(
                standard = KeyVariant("$", "E (kapalı)", true, "bottom"),
                shift = KeyVariant("$", "E (kapalı)", true, "bottom"),
                altgr = KeyVariant("$", "E (kapalı)", true, "bottom"),
                shiftAltgr = KeyVariant("$", "E (kapalı)", true, "bottom")
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
                shift = KeyVariant("&", "U", false, null),
                altgr = KeyVariant("&", "U", false, null),
                shiftAltgr = KeyVariant("&", "U", false, null)
            ),
        KeyCell(
                standard = KeyVariant("%", "I", false, null),
                shift = KeyVariant("%", "I", false, null),
                altgr = KeyVariant("%", "I", false, null),
                shiftAltgr = KeyVariant("%", "I", false, null)
            ),
        KeyCell(
                standard = KeyVariant("^", "O", false, null),
                shift = KeyVariant("^", "O", false, null),
                altgr = KeyVariant("^", "O", false, null),
                shiftAltgr = KeyVariant("^", "O", false, null)
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
                shift = KeyVariant("Õ", "Ü", false, null),
                altgr = KeyVariant("Ô", "Ü", false, null),
                shiftAltgr = KeyVariant("Ô", "Ü", false, null)
            ),
    )
    val rowC: List<KeyCell> = listOf(
        KeyCell(
                standard = KeyVariant("#", "A", false, null),
                shift = KeyVariant("#", "A", false, null),
                altgr = KeyVariant("#", "A", false, null),
                shiftAltgr = KeyVariant("#", "A", false, null)
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
                shift = KeyVariant("L", "Nokta", false, null),
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
                shift = KeyVariant("È%", "İ", false, null),
                altgr = KeyVariant("È%", "İ", false, null),
                shiftAltgr = KeyVariant("È%", "İ", false, null)
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
                standard = KeyVariant("ü", "Ára (kuyruk)", true, null),
                shift = KeyVariant("ü", "Ára (kuyruk)", true, null),
                altgr = KeyVariant("ü", "Ára (kuyruk)", true, null),
                shiftAltgr = KeyVariant("ü", "Ára (kuyruk)", true, null)
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
                shift = KeyVariant("^È", "Ö", false, null),
                altgr = KeyVariant("^È", "Ö", false, null),
                shiftAltgr = KeyVariant("^È", "Ö", false, null)
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