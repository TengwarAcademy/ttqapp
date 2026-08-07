# -*- coding: utf-8 -*-
import json

with open('/home/claude/shared/keymap.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

def esc(s):
    if s is None:
        return None
    return s.replace('\\', '\\\\').replace('"', '\\"').replace('\n', '\\n')

def kt_variant(v):
    glyph = v.get("glyph")
    label = v.get("label", "")
    desc = "true" if v.get("desc") else "false"
    labelPos = v.get("labelPos")
    glyph_kt = f'"{esc(glyph)}"' if glyph is not None else "null"
    labelPos_kt = f'"{esc(labelPos)}"' if labelPos is not None else "null"
    return f'KeyVariant({glyph_kt}, "{esc(label)}", {desc}, {labelPos_kt})'

def kt_cell(c):
    return (f'KeyCell(\n'
            f'        standard = {kt_variant(c["standard"])},\n'
            f'        shift = {kt_variant(c["shift"])},\n'
            f'        altgr = {kt_variant(c["altgr"])},\n'
            f'        shiftAltgr = {kt_variant(c["shiftAltgr"])}\n'
            f'    )')

lines = []
lines.append("// OTOMATİK ÜRETİLDİ — TTQ-Klavye.txt dosyasındaki tuş haritasından dönüştürüldü.")
lines.append("// Elle düzenlemeyin; kaynağı değiştirip yeniden üretin.")
lines.append("package com.tengwar.klavye")
lines.append("")
lines.append("data class KeyVariant(")
lines.append("    val glyph: String?,")
lines.append("    val label: String,")
lines.append("    val desc: Boolean = false,")
lines.append("    val labelPos: String? = null")
lines.append(")")
lines.append("")
lines.append("data class KeyCell(")
lines.append("    val standard: KeyVariant,")
lines.append("    val shift: KeyVariant,")
lines.append("    val altgr: KeyVariant,")
lines.append("    val shiftAltgr: KeyVariant")
lines.append(")")
lines.append("")
lines.append("object KeymapData {")
for row_name in ["A", "B", "C", "D"]:
    row = data["rows"][row_name]
    lines.append(f"    val row{row_name}: List<KeyCell> = listOf(")
    for c in row:
        cell_kt = kt_cell(c)
        indented = "\n".join(("        " + ln if i > 0 else "        " + ln) for i, ln in enumerate(cell_kt.split("\n")))
        lines.append(indented + ",")
    lines.append("    )")
lines.append("}")

out = "\n".join(lines)
with open('/home/claude/shared/KeymapData.kt', 'w', encoding='utf-8') as f:
    f.write(out)
print("OK, satır sayısı:", len(lines))
