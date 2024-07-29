package eu.itreegroup.spark.dao.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.s2.server.rest.model.Spr_paging_ot;

public class SelectRequestParams {

    public static final String PARAM_QUICK_SEARCH = "quickSearch";

    private Spr_paging_ot pagingParams;

    private List<List<String>> params;

    @JsonIgnore
    private HashMap<String, String> paramList;

    private ArrayList<AdvancedSearchParameterStatement> extendedParams;

    public SelectRequestParams() {

    }

    public SelectRequestParams(Spr_paging_ot pagingParams) {
        setPagingParams(pagingParams);
    }

    public Spr_paging_ot getPagingParams() {
        return pagingParams;
    }

    public void setPagingParams(Spr_paging_ot paging) {
        this.pagingParams = paging;
    }

    public List<List<String>> getParams() {
        return params;
    }

    public void setParams(List<List<String>> params) {
        this.params = params;
        if (this.params != null) {
            this.paramList = new HashMap<String, String>();
            for (int i = 0; i < this.params.size(); i++) {
                List<String> param = this.params.get(i);
                if (param.size() == 2 && param.get(1) != null) {
                    paramList.put(param.get(0), param.get(1));
                }
            }
        }
    }

    public HashMap<String, String> getParamList() {
        return this.paramList;
    }

    @JsonIgnore
    public HashMap<String, AdvancedSearchParameterStatement> getAdvancedParameters() {
        HashMap<String, AdvancedSearchParameterStatement> params = new HashMap<String, AdvancedSearchParameterStatement>();
        if (extendedParams != null) {
            for (int i = 0; i < extendedParams.size(); i++) {
                AdvancedSearchParameterStatement obj = extendedParams.get(i);
                if (obj != null) {
                    params.put(obj.getParamName(), obj);
                }
            }

        }
        return params;
    }

    public ArrayList<AdvancedSearchParameterStatement> getExtendedParams() {
        return extendedParams;
    }

    public void setExtendedParams(ArrayList<AdvancedSearchParameterStatement> extendedParams) {
        this.extendedParams = extendedParams;
    }

    public void setParamList(HashMap<String, String> paramList) {
        this.paramList = paramList;
    }

    @Override
    public String toString() {
        return "paging: " + this.pagingParams + " params: " + params;
    }

}
