package com.example.comicbe.service.chapterView;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.repository.MangaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class ChapterViewServiceImpl implements com.example.comicbe.service.ChapterViewServiceImpl {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MangaRepository mangaRepository;

    private static final String VIEW_KEY = "chapter:view:"; // chapter:view:123 = 50 views

    @Override
    public void increaseView(Long mangaId) {
        String key = VIEW_KEY + mangaId;
        if (!redisTemplate.hasKey(key)) {
            Long dbView = mangaRepository.findById(mangaId).get().getTotalView();

            // Chỉ set nếu key chưa tồn tại (race-safe)
            redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(dbView != null ? dbView : 0L));
        }
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Map<Long, Long> fetchAllViews(List<Long> mangaIds) {
        // Lấy toàn bộ key chapter:view:*
        Set<String> keys = redisTemplate.keys(VIEW_KEY + "*");

        Map<Long, Long> result = new HashMap<>();

        if (keys != null) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) {
                    Long mangaId = Long.valueOf(key.substring(VIEW_KEY.length()));
                    result.put(mangaId, Long.valueOf(value));
                }
            }
        }

        return result;
    }

    @Override
    public Long fetchViewManga(Long mangaId) {
        String key = VIEW_KEY + mangaId;
        if (redisTemplate.hasKey(key)) {
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.hasText(value)){
                return Long.valueOf(value);
            }
        }

        return 0L;
    }

    @Override
    public void clearView(Long mangaId) {
        redisTemplate.delete(VIEW_KEY + mangaId);
    }


    @Scheduled(fixedRate = 30000)
    @Transactional
    public void flushViewsToDatabase() {
        Map<Long, Long> viewMap = this.fetchAllViews(new ArrayList<>());

        if (viewMap.isEmpty()) {
            return;
        }

        List<Manga> mangas = new ArrayList<>();
        mangas = mangaRepository.findAllById(viewMap.keySet());

        mangas.stream().forEach(manga -> {
            if (viewMap.containsKey(manga.getId())){
                if (manga.getTotalView() != null && manga.getTotalView() < viewMap.get(manga.getId())){
                    manga.setTotalView(viewMap.get(manga.getId()));
                } else if (manga.getTotalView() == null) {
                    manga.setTotalView(viewMap.get(manga.getId()));
                }
            }
        });
        mangaRepository.saveAll(mangas);

        log.info("done batch upadate view");
    }
}
