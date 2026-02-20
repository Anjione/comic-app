//package com.example.comicbe.utils.validator;
//
//import com.ielts.server.dto.filter.NewsFilter;
//import com.ielts.server.dto.news.NewsRequest;
//import com.ielts.server.jpa.entity.News;
//import com.ielts.server.jpa.repository.NewsRepository;
//import com.ielts.server.jpa.spectification.spec.NewSpec;
//import com.ielts.server.utils.AppException;
//import com.ielts.server.utils.ErrorCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
//@Component
//public class NewsValidator {
//
//    @Autowired
//    private NewsRepository newsRepository;
//
//    public void validatorDuplicate(NewsRequest request) {
//        NewsFilter filter = NewsFilter.builder().title(request.getTitle()).build();
//        NewSpec newSpec = new NewSpec(filter);
//
//        List<News> news = newsRepository.findAll(newSpec);
//        if (!CollectionUtils.isEmpty(news)) {
//            throw new AppException(ErrorCode.NEWS_TITLE_DUPLICATE);
//        }
//    }
//
//    public void validatorDuplicateUpdate(Long id, NewsRequest request) {
//        NewsFilter filter = NewsFilter.builder().title(request.getTitle()).build();
//        NewSpec newSpec = new NewSpec(filter);
//
//        List<News> news = newsRepository.findAll(newSpec);
//        if (news.stream().noneMatch(item -> item.getId().equals(id)) && !CollectionUtils.isEmpty(news)) {
//            throw new AppException(ErrorCode.NEWS_TITLE_DUPLICATE);
//        }
//    }
//
//    public News validatorExists(Long id) {
//        return newsRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
//    }
//}
