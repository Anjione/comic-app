package com.example.comicbe.controller.cache;

import com.example.comicbe.service.chapterView.ViewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ViewServiceImpl chapterViewService;

//    @DeleteMapping("/clear/{cacheName}")
//    public String clearCacheByName(@PathVariable String cacheName) {
//        cacheService.clearCache(cacheName);
//        return "Cleared cache: " + cacheName;
//    }

    @DeleteMapping("/clear-all")
    public String clearAll() {
        this.clearAllCaches();
        return "All caches cleared";
    }

    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(name -> {
            Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                cache.clear();
            }
        });

//        chapterViewService.clearView(1L);
    }
}
