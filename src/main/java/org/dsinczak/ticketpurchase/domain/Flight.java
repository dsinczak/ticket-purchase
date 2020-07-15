package org.dsinczak.ticketpurchase.domain;

import io.vavr.collection.Set;
import lombok.Value;
import org.javamoney.moneta.Money;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Value
public class Flight {
    FlightId flightId;
    Money price;
    LocalTime time;
    Set<DayOfWeek> days;
    Route route;

    @Value
    public static class FlightId {
        UUID uuid;

        public static FlightId random() {
            return new FlightId(UUID.randomUUID());
        }
    }

    @Value
    public static class Route {

        Country from;
        Country to;

        public enum Continent {
            EU, AS, NA, AF, AN, SA, OC
        }

        public boolean isToContinent(Continent continent) {
            return continent.equals(to.continent);
        }

        // i found it on internet
        public enum Country {
            AD(Continent.EU), AE(Continent.AS), AF(Continent.AS), AG(Continent.NA), AI(Continent.NA), AL(Continent.EU), AM(Continent.AS), AN(Continent.NA), AO(Continent.AF), AP(Continent.AS), AQ(Continent.AN), AR(Continent.SA), AS(Continent.OC), AT(Continent.EU), AU(Continent.OC), AW(Continent.NA), AX(Continent.EU), AZ(Continent.AS),
            BA(Continent.EU), BB(Continent.NA), BD(Continent.AS), BE(Continent.EU), BF(Continent.AF), BG(Continent.EU), BH(Continent.AS), BI(Continent.AF), BJ(Continent.AF), BL(Continent.NA), BM(Continent.NA), BN(Continent.AS), BO(Continent.SA), BR(Continent.SA), BS(Continent.NA), BT(Continent.AS), BV(Continent.AN), BW(Continent.AF), BY(Continent.EU), BZ(Continent.NA),
            CA(Continent.NA), CC(Continent.AS), CD(Continent.AF), CF(Continent.AF), CG(Continent.AF), CH(Continent.EU), CI(Continent.AF), CK(Continent.OC), CL(Continent.SA), CM(Continent.AF), CN(Continent.AS), CO(Continent.SA), CR(Continent.NA), CU(Continent.NA), CV(Continent.AF), CX(Continent.AS), CY(Continent.AS), CZ(Continent.EU),
            DE(Continent.EU), DJ(Continent.AF), DK(Continent.EU), DM(Continent.NA), DO(Continent.NA), DZ(Continent.AF),
            EC(Continent.SA), EE(Continent.EU), EG(Continent.AF), EH(Continent.AF), ER(Continent.AF), ES(Continent.EU), ET(Continent.AF), EU(Continent.EU), FI(Continent.EU),
            FJ(Continent.OC), FK(Continent.SA), FM(Continent.OC), FO(Continent.EU), FR(Continent.EU), FX(Continent.EU),
            GA(Continent.AF), GB(Continent.EU), GD(Continent.NA), GE(Continent.AS), GF(Continent.SA), GG(Continent.EU), GH(Continent.AF), GI(Continent.EU), GL(Continent.NA), GM(Continent.AF), GN(Continent.AF), GP(Continent.NA), GQ(Continent.AF), GR(Continent.EU), GS(Continent.AN), GT(Continent.NA), GU(Continent.OC), GW(Continent.AF), GY(Continent.SA),
            HK(Continent.AS), HM(Continent.AN), HN(Continent.NA), HR(Continent.EU), HT(Continent.NA), HU(Continent.EU),
            ID(Continent.AS), IE(Continent.EU), IL(Continent.AS), IM(Continent.EU), IN(Continent.AS), IO(Continent.AS), IQ(Continent.AS), IR(Continent.AS), IS(Continent.EU), IT(Continent.EU),
            JE(Continent.EU), JM(Continent.NA), JO(Continent.AS), JP(Continent.AS),
            KE(Continent.AF), KG(Continent.AS), KH(Continent.AS), KI(Continent.OC), KM(Continent.AF), KN(Continent.NA), KP(Continent.AS), KR(Continent.AS), KW(Continent.AS), KY(Continent.NA), KZ(Continent.AS),
            LA(Continent.AS), LB(Continent.AS), LC(Continent.NA), LI(Continent.EU), LK(Continent.AS), LR(Continent.AF), LS(Continent.AF), LT(Continent.EU), LU(Continent.EU), LV(Continent.EU), LY(Continent.AF),
            MA(Continent.AF), MC(Continent.EU), MD(Continent.EU), ME(Continent.EU), MF(Continent.NA), MG(Continent.AF), MH(Continent.OC), MK(Continent.EU), ML(Continent.AF), MM(Continent.AS), MN(Continent.AS), MO(Continent.AS), MP(Continent.OC), MQ(Continent.NA), MR(Continent.AF), MS(Continent.NA), MT(Continent.EU), MU(Continent.AF), MV(Continent.AS), MW(Continent.AF), MX(Continent.NA), MY(Continent.AS), MZ(Continent.AF),
            NA(Continent.AF), NC(Continent.OC), NE(Continent.AF), NF(Continent.OC), NG(Continent.AF), NI(Continent.NA), NL(Continent.EU), NO(Continent.EU), NP(Continent.AS), NR(Continent.OC), NU(Continent.OC), NZ(Continent.OC),
            OM(Continent.AS),
            PA(Continent.NA), PE(Continent.SA), PF(Continent.OC), PG(Continent.OC), PH(Continent.AS), PK(Continent.AS), PL(Continent.EU), PM(Continent.NA), PN(Continent.OC), PR(Continent.NA), PS(Continent.AS), PT(Continent.EU), PW(Continent.OC), PY(Continent.SA),
            QA(Continent.AS),
            RE(Continent.AF), RO(Continent.EU), RS(Continent.EU), RU(Continent.EU), RW(Continent.AF),
            SA(Continent.AS), SB(Continent.OC), SC(Continent.AF), SD(Continent.AF), SE(Continent.EU), SG(Continent.AS), SH(Continent.AF), SI(Continent.EU), SJ(Continent.EU), SK(Continent.EU), SL(Continent.AF), SM(Continent.EU), SN(Continent.AF), SO(Continent.AF), SR(Continent.SA), ST(Continent.AF), SV(Continent.NA), SY(Continent.AS), SZ(Continent.AF),
            TC(Continent.NA), TD(Continent.AF), TF(Continent.AN), TG(Continent.AF), TH(Continent.AS), TJ(Continent.AS), TK(Continent.OC), TL(Continent.AS), TM(Continent.AS), TN(Continent.AF), TO(Continent.OC), TR(Continent.EU), TT(Continent.NA), TV(Continent.OC), TW(Continent.AS), TZ(Continent.AF),
            UA(Continent.EU), UG(Continent.AF), UM(Continent.OC), US(Continent.NA), UY(Continent.SA), UZ(Continent.AS),
            VA(Continent.EU), VC(Continent.NA), VE(Continent.SA), VG(Continent.NA), VI(Continent.NA), VN(Continent.AS), VU(Continent.OC),
            WF(Continent.OC), WS(Continent.OC),
            YE(Continent.AS), YT(Continent.AF),
            ZA(Continent.AF), ZM(Continent.AF), ZW(Continent.AF);

            private Continent continent;

            Country(Continent continent) {
                this.continent = continent;
            }
        }
    }
}
