# -*- coding: utf-8 -*-
import json

E = {"glyph": None, "label": ""}

def same(glyph, label, desc=False, labelPos=None):
    v = {"glyph": glyph, "label": label}
    if desc: v["desc"] = True
    if labelPos: v["labelPos"] = labelPos
    return {"standard": v, "shift": v, "altgr": v, "shiftAltgr": v}

def cell(standard=None, shift=None, altgr=None, shiftAltgr=None):
    return {
        "standard": standard if standard is not None else E,
        "shift": shift if shift is not None else E,
        "altgr": altgr if altgr is not None else E,
        "shiftAltgr": shiftAltgr if shiftAltgr is not None else E,
    }

def g(glyph, label, desc=False, labelPos=None):
    v = {"glyph": glyph, "label": label}
    if desc: v["desc"] = True
    if labelPos: v["labelPos"] = labelPos
    return v

ROW_A = [
    cell(g('«','Tırnak'), g('º','Ára (nokta)'), g('P','Çiftleme 1'), None),
    cell(g('ñ','1'), g('Á','Ünlem İşareti'), g(':','Çiftleme 2'), None),
    cell(g('ò','2'), g('²','Kesme İşareti'), g('p','Çiftleme 3'), None),
    cell(g('ó','3'), None, g('°','Çiftleme 4'), None),
    cell(g('ô','4'), None, g(';','Çiftleme 5'), None),
    cell(g('õ','5'), None, None, None),
    cell(g('ö','6'), None, None, None),
    cell(g('÷','7'), g('›','Alt. Parantez'), None, None),
    cell(g('ø','8'), g('Œ','Parantez Aç'), None, None),
    cell(g('ù','9'), g('œ','Parantez Kapa'), None, None),
    cell(g('ð','0'), g('¬','Eşittir'), None, None),
    cell(None, g('À','Soru İşareti'), None, None),
    cell(g('Â','Tire'), g('Ì','Yumuşatma'), g('Í','Yumuşatma'), g('Î','Yumuşatma')),
]

ROW_B = [
    cell(g('`','Telco'), g('~','Ára'), None, None),
    same('Ø','E (açık)', desc=True, labelPos='bottom'),
    same('$','E (kapalı)', desc=True, labelPos='bottom'),
    cell(g('7','R'), g('u','R*'), None, None),
    cell(g('1','T'), g('!','Gen. T'), None, None),
    cell(g('l','Y'), None, None, None),
    same('&','U'),
    same('%','I'),
    same('^','O'),
    cell(g('q','P'), g('Q','Gen. P'), None, None),
    cell(g('b','Ğ'), None, None, None),
    cell(g('Ô','Ü'), g('Õ','Ü'), g('Ô','Ü'), g('Ô','Ü')),
]

ROW_C = [
    same('#','A'),
    cell(g('8','S'), g('i','S (Ters)'), None, None),
    cell(g('2','D'), g('@','Gen. D'), None, None),
    cell(g('e','F'), None, None, None),
    cell(g('x','G'), g('X','Gen. G'), None, None),
    cell(g('9','H'), None, None, None),
    cell(g('f','J'), None, None, None),
    cell(g('z','K'), g('Z','Gen. K'), None, None),
    cell(g('j','L'), g('L','Nokta'), None, None),
    cell(g('d','Ş'), None, None, None),
    same('È%','İ'),
    cell(g('¹','Virgül'), g('Ã','Noktalı Virgül'), g('ˆ','Üç Nokta'), None),
]

ROW_D = [
    same('ü','Ára (kuyruk)', desc=True),
    cell(g('k','Z'), g('<','Z*'), None, None),
    cell(g('K','Z*'), g(',','Z* (Ters)'), None, None),
    cell(g('s','C'), g('S','Gen. C'), None, None),
    cell(g('r','V'), None, None, None),
    cell(g('w','B'), g('W','Gen. B'), None, None),
    cell(g('5','N'), None, None, None),
    cell(g('t','M'), None, None, None),
    same('^È','Ö'),
    cell(g('a','Ç'), g('A','Gen. Ç'), None, None),
    cell(g('·','Nokta'), g('-','İki Nokta'), g('Ë','Nokta'), g('Ê','Nokta')),
]

data = {"rows": {"A": ROW_A, "B": ROW_B, "C": ROW_C, "D": ROW_D}}

with open('/home/claude/shared/keymap.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=2)

print("OK", sum(len(v) for v in data["rows"].values()), "tuş")
