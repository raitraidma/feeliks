package feeliks.analyzer.service.analyzer;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionAnalyzerTest {
    @Test
    public void stripWikiTags() throws Exception {
        String wikiText = "{{See artikkel| räägib matemaatika mõistest; muude tähenduste kohta vaata artiklit [[Maatriks (täpsustus)]].}} {{ToimetaAeg|kuu=veebruar|aasta=2008}} '''Maatriks''' on [[ristkülik]]ukujuline [[tabel]], mis koosneb [[arv]]udest (tavaliselt [[reaalarv]]udest või [[kompleksarv]]udest) või mingitest muudest etteantud [[hulk|hulga]] [[element (matemaatika)|element]]idest, sealhulgas näiteks [[polünoom]]idest, [[funktsioon (matemaatika)|funktsioon]]idest, [[diferentsiaal]]idest, [[vektor]]itest. Tabeli sissekandeid nimetatakse '''maatriksi elementideks''' või '''maatriksi komponentideks'''. Maatriksi elementide tehete (liitmine ja lahutamine, korrutamine ja jagamine) kaudu on võimalik defineerida ka tehted maatriksitega.";
        String strippedText = "Maatriks on ristkülikukujuline tabel, mis koosneb arvudest või mingitest muudest etteantud hulga elementidest, sealhulgas näiteks polünoomidest, funktsioonidest, diferentsiaalidest, vektoritest. Tabeli sissekandeid nimetatakse maatriksi elementideks või maatriksi komponentideks. Maatriksi elementide tehete kaudu on võimalik defineerida ka tehted maatriksitega.";
        assertEquals(strippedText, new QuestionAnalyzer(0, null).stripWikiTags(wikiText));
    }
}