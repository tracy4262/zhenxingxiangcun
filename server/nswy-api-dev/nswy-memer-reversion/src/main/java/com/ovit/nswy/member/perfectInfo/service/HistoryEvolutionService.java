package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface HistoryEvolutionService {



    Map<String,Object> findHistoryEvolution(Map<String,Object> params);

    void updateHistoryEvolutionName(Map<String,Object> params);

    void saveHistoryEvolution(Map<String,Object> params);

    void saveTextPreview(Map<String,Object> params);

    void deleteHistoryEvolution(Map<String,Object> params);
}
