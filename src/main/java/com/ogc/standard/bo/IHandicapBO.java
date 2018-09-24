package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Handicap;
import com.ogc.standard.domain.HandicapGrade;
import com.ogc.standard.domain.SimuOrder;

public interface IHandicapBO extends IPaginableBO<Handicap> {

    public void saveHandicap(SimuOrder simuOrder);

    public void stuffHandicap(String symbol, String toSymbol, String direction,
            int stuffHandicapQuantity);

    public int removeHandicap(String code);

    public int refreshHandicap(Handicap data);

    public List<Handicap> queryHandicapList(Handicap condition);

    public List<HandicapGrade> queryHandicapGradeList(String symbol,
            String toSymbol, String direction);

    public List<Handicap> queryHandicapList(String symbol, String toSymbol,
            String direction, int start, int limit);
}
