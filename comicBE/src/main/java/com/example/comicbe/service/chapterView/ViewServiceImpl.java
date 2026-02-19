package com.example.comicbe.service.chapterView;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.service.MangaService;
import com.example.comicbe.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    ExecutorService executorGL = Executors.newVirtualThreadPerTaskExecutor();


    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaService mangaService;

    private static final String VIEW_KEY = "manga:view:total:"; // chapter:view:123 = 50 views
    private static final String VIEW_KEY_PORPULAR_DAY = "manga:view:day:";
    private static final String VIEW_KEY_PORPULAR_WEEK = "manga:view:week:";
    private static final String VIEW_KEY_PORPULAR_MOUNTH = "manga:view:month:";
    private static final String VIEW_KEY_PORPULAR_YEAR = "manga:view:year:";

    @Override
    public void increaseView(Long mangaId) {
        String key = VIEW_KEY + mangaId;
        String dateNow = formatDateSilently(Timestamp.from(Instant.now()));

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());

        if (!redisTemplate.hasKey(key)) {
            Long dbView = mangaRepository.findById(mangaId).get().getTotalView();

            // Chỉ set nếu key chưa tồn tại (race-safe)
            redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(dbView != null ? dbView : 0L));
        }
        redisTemplate.opsForValue().increment(key);

    }


    @Override
    public void increaseViewV2(Long mangaId) {
        LocalDate today = LocalDate.now();

        String dayKey = VIEW_KEY_PORPULAR_DAY + today;

        int week = today.get(WeekFields.ISO.weekOfWeekBasedYear());
        int year = today.get(WeekFields.ISO.weekBasedYear());
        String weekKey = VIEW_KEY_PORPULAR_WEEK + year + "-" + week;

        String monthKey = VIEW_KEY_PORPULAR_MOUNTH + YearMonth.from(today);
        String yearKey = VIEW_KEY_PORPULAR_YEAR + today.getYear();

        String totalKey = VIEW_KEY;


        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer =
                    (RedisSerializer<String>) redisTemplate.getStringSerializer();
            byte[] member = serializer.serialize(String.valueOf(mangaId));

            connection.zIncrBy(serializer.serialize(dayKey), 1, member);
            connection.zIncrBy(serializer.serialize(weekKey), 1, member);
            connection.zIncrBy(serializer.serialize(monthKey), 1, member);
            connection.zIncrBy(serializer.serialize(yearKey), 1, member);
            connection.zIncrBy(serializer.serialize(totalKey), 1, member);
            return null;
        });
    }

    @Override
    public List<Long> listMangaPopular(String dataKey, long start, long end) {
        LocalDate today = LocalDate.now();
        String dayKey = VIEW_KEY_PORPULAR_DAY + today;

        int week = today.get(WeekFields.ISO.weekOfWeekBasedYear());
        int year = today.get(WeekFields.ISO.weekBasedYear());
        String weekKey = VIEW_KEY_PORPULAR_WEEK + year + "-" + week;

        String monthKey = VIEW_KEY_PORPULAR_MOUNTH + YearMonth.from(today);
        String yearKey = VIEW_KEY_PORPULAR_YEAR + today.getYear();

        String totalKey = VIEW_KEY;


        Set<ZSetOperations.TypedTuple<String>> top10 =
                redisTemplate.opsForZSet()
                        .reverseRangeWithScores(dataKey, start, end);

        if (top10 == null || top10.isEmpty()) {
            return Collections.emptyList();
        }

        return top10.stream()
                .map(ZSetOperations.TypedTuple::getValue) // "123"
                .map(Long::valueOf)                        // 123L
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Long>> getPopularAll(long start, long end) {
        LocalDate today = LocalDate.now();
        String dayKey = VIEW_KEY_PORPULAR_DAY + today;

        int week_ = today.get(WeekFields.ISO.weekOfWeekBasedYear());
        int year_ = today.get(WeekFields.ISO.weekBasedYear());
        String weekKey = VIEW_KEY_PORPULAR_WEEK + year_ + "-" + week_;

        String monthKey = VIEW_KEY_PORPULAR_MOUNTH + YearMonth.from(today);
        String yearKey = VIEW_KEY_PORPULAR_YEAR + today.getYear();

        String totalKey = VIEW_KEY;
        List<Object> results = redisTemplate.executePipelined(
                (RedisCallback<Object>) connection -> {

                    RedisSerializer<String> serializer =
                            (RedisSerializer<String>) redisTemplate.getStringSerializer();

                    byte[] day = serializer.serialize(dayKey);
                    byte[] week = serializer.serialize(weekKey);
                    byte[] month = serializer.serialize(monthKey);
                    byte[] year = serializer.serialize(yearKey);
                    byte[] total = serializer.serialize(totalKey);

                    connection.zRevRange(day, 0, 14);
                    connection.zRevRange(week, start, end);
                    connection.zRevRange(month, start, end);
                    connection.zRevRange(year, start, end);
                    connection.zRevRange(total, start, end);

                    return null;
                }
        );
        log.info("result : {}", results);

        return Map.of(
                "day", castToLongList(results.get(0)),
                "week", castToLongList(results.get(1)),
                "month", castToLongList(results.get(2)),
                "year", castToLongList(results.get(3)),
                "total", castToLongList(results.get(4))
        );
    }

    @SuppressWarnings("unchecked")
    private List<Long> castToLongList(Object obj) {
        if (!(obj instanceof Collection<?> collection)) {
            return List.of();
        }

        return collection.stream()
                .map(String.class::cast)
                .map(Long::valueOf)
                .toList();
    }

    private String formatDateSilently(Date date, String format) {
        try {
            if (date == null) {
                return null;
            } else {
                if (format == null) {
                    format = "yyyy-MM-dd";
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                Instant instant = date.toInstant();
                LocalDateTime ldt = instant.atZone(ZoneId.of("GMT+7")).toLocalDateTime();
                return ldt.format(formatter);
            }
        } catch (Exception var5) {
            return null;
        }
    }

    public String formatDateSilently(Date date) {
        return formatDateSilently((Date) date, (String) null);
    }

    public String formatDateSilently(long seconds) {
        return formatDateSilently((Date) (new Date(seconds)), (String) null);
    }

    public String formatDateSilently(Timestamp date) {
        return date == null ? null : formatDateSilently(new Date(date.getTime()));
    }

    @Override
    public Map<Long, Long> fetchAllViews(List<Long> mangaIds) throws ExecutionException, InterruptedException {
        if (mangaIds == null || mangaIds.isEmpty()) {
            return Map.of();
        }

        Map<Long, Long> result = new ConcurrentHashMap<>();

        List<Future<?>> futures = new ArrayList<>();

        for (Long mangaId : mangaIds) {
            futures.add(
                    executorGL.submit(() ->
                            result.put(mangaId, fetchViewManga(mangaId))
                    )
            );
        }

        for (Future<?> f : futures) {
            f.get();
        }

//        List<String> keys = mangaIds.stream()
//                .map(id -> VIEW_KEY + id)
//                .toList();
//
//        List<Object> values = redisTemplate.executePipelined(
//                (RedisCallback<Object>) connection -> {
//                    RedisSerializer<String> serializer =
//                            (RedisSerializer<String>) redisTemplate.getStringSerializer();
//                    for (String key : keys) {
//                        connection.zRevRange(serializer.serialize(key), 0, -1); // lấy HẾT
//                    }
//                    return null;
//                }
//        );
//        List<Set<String>> nonEmptyValues = values.stream()
//                .filter(v -> v instanceof Set<?> set && !set.isEmpty())
//                .map(v -> (Set<String>) v)
//                .toList();
//
//        log.info("list view update : {}", nonEmptyValues);
//
//        Map<Long, Long> result = new HashMap<>();
//
//        for (int i = 0; i < keys.size(); i++) {
//            String value = values.get(i).toString();
//            if (value != null) {
//                result.put(mangaIds.get(i), Long.valueOf(value));
//            }
//        }
        log.info("size view update : {}", result.size());
        return result;
    }

    @Override
    public Long fetchViewManga(Long mangaId) {
        String key = VIEW_KEY;
        if (redisTemplate.hasKey(key)) {
            Double score = redisTemplate.opsForZSet().score(key, mangaId.toString());
            return score != null ? score.longValue() : 0L;
        }

        return 0L;
    }

    @Override
    public void clearView(Long mangaId) {
        redisTemplate.delete(VIEW_KEY + mangaId);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 */6 * * *")
    public void rebuildTotalView() {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer =
                    (RedisSerializer<String>) redisTemplate.getStringSerializer();
            List<Object[]> data = mangaRepository.findAllViewCount();

            byte[] key = serializer.serialize(VIEW_KEY);
            connection.del(key);

            for (Object[] row : data) {
                String mangaId = row[0].toString();
                Double view = ((Number) row[1]).doubleValue();

                connection.zAdd(
                        key,
                        view,
                        serializer.serialize(mangaId)
                );
            }
            return null;
        });
    }

    @Override
    @Transactional
    public void syncView(Manga manga) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer =
                    (RedisSerializer<String>) redisTemplate.getStringSerializer();
            List<Object[]> data = mangaRepository.findAllViewCount();

            byte[] key = serializer.serialize(VIEW_KEY);
            connection.del(key);

//            for (Object[] row : data) {
            String mangaId = manga.getId().toString();
            Double view = manga.getTotalView().doubleValue();

            connection.zAdd(
                    key,
                    view,
                    serializer.serialize(mangaId)
            );
//            }
            return null;
        });
    }


    @Scheduled(cron = "0 */15 * * * *")
    @Transactional
    public void flushViewsToDatabase() throws ExecutionException, InterruptedException {
        log.info("job update view");
        List<Long> ids = mangaRepository.findAllIds();
        Map<Long, Long> viewMap = this.fetchAllViews(ids);

        if (viewMap.isEmpty()) {
            return;
        }

        List<Manga> mangas = new ArrayList<>();
        mangas = mangaRepository.findAllById(viewMap.keySet());

        mangas.stream().forEach(manga -> {
            if (viewMap.containsKey(manga.getId())) {
                if (manga.getTotalView() != null && manga.getTotalView() < viewMap.get(manga.getId())) {
                    long totalView = viewMap.get(manga.getId()) + manga.getTotalView();
                    manga.setTotalView(totalView);
                } else if (manga.getTotalView() != null && manga.getTotalView() > viewMap.get(manga.getId())) {
                    long totalView = manga.getTotalView() + viewMap.get(manga.getId());
                    manga.setTotalView(totalView);

                    this.syncView(manga);
                } else if (manga.getTotalView() == null) {
                    manga.setTotalView(viewMap.get(manga.getId()));
                }
            }
        });
        mangaRepository.saveAll(mangas);

        log.info("done batch upadate view");
    }


    @Transactional
    @Scheduled(cron = "1 0 0 * * *")
    @Override
    public void jobPushViewDaily() {
//        List<MangaDto> mangaDtos = mangaService.getLastUpdate().getData();
        Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "modifiedBy"));
        List<Manga> mangas = mangaRepository.findAll(pageable).getContent();
        for (Manga manga : mangas){
            this.increaseViewV2(manga.getId());
        }
    }
}
