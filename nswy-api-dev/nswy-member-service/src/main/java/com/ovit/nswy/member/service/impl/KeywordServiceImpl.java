package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.KeywordMapper;
import com.ovit.nswy.member.service.KeywordService;
import org.ahocorasick.trie.Token;
import org.ahocorasick.trie.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    private static Cache<String, List<String>> cache;
    @Autowired
    private KeywordMapper keywordMapper;
    private Trie trie;

    @PostConstruct
    public void initial() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, List<String>> config = new MutableConfiguration();
        cache = cacheManager.createCache("keywords", config);
        List<String> list = cache.get("keywords");
        if (null == list || list.isEmpty()) {
            // TODO 查询数据 放入jcache
            list = keywordMapper.listKeyWords();
            cache.put("keywords", list);
        }
        Trie.TrieBuilder builder = Trie.builder().ignoreOverlaps();
        for (String str : list) {
            builder.addKeyword(str);
        }
        trie = builder.build();
    }

    @Override
    public List<String> validate(String content) {
        initial();
        Collection<Token> tokens = trie.tokenize(content);
        List<String> result = new ArrayList<String>();
        for (Token token : tokens) {
            if (token.isMatch()) {
                result.add(token.getFragment());
            }
        }
        return result;
    }

}
