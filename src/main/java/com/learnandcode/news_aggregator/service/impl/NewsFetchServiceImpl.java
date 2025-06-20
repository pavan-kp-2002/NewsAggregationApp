package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.factory.NewsApiHandlerFactory;
import com.learnandcode.news_aggregator.model.*;
import com.learnandcode.news_aggregator.repositories.*;
import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import com.learnandcode.news_aggregator.service.NewsFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsFetchServiceImpl implements NewsFetchService {
    @Autowired
    private ExternalServerRepository externalServerRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NewsApiHandlerFactory handlerFactory;
    @Autowired
    private UserCategoryConfigurationRepository userNotificationConfigurationRepo;
    @Autowired
    private UserKeywordConfigurationRepository userKeywordConfigurationRepo;
    @Autowired
    private NotificationRepository notificationRepository;


    //private final long fetchInterval = 4 * 60 * 60 * 1000;
    private final long testInterval = 1 * 60 * 60 * 1000; // for testing
    @Override
    @Scheduled(fixedRate = testInterval)
    public void fetchArticlesFromAllExternalApis() {
       List<ExternalServer> externalServers = externalServerRepository.findAll();

       for (ExternalServer server : externalServers) {
            try {
                ExternalNewsApiHandler handler = handlerFactory.getHandler(server.getServerName());
                List<Article> articles = handler.fetchArticles(server);
                articleRepository.saveAll(articles);

                for (Article article : articles) {
                    List<Notification> notificationsToSave = new ArrayList<>();

                    if (article.getCategoryId() != null) {
                        List<UserCategoryConfiguration> categoryConfigs =
                                userNotificationConfigurationRepo.findByCategoryAndNotificationConfigurationStatus(
                                        article.getCategoryId(), NotificationConfigurationStatus.ENABLED);

                        for (UserCategoryConfiguration config : categoryConfigs) {
                            Notification notification = new Notification();
                            notification.setArticle(article);
                            notification.setUser(config.getUser());
                            notification.setNotificationRead(false);
                            notification.setEmailSent(false);
                            notificationsToSave.add(notification);
                        }
                    }

                    // 2. Keyword-based notifications
                    List<UserKeywordConfiguration> keywordConfigs = userKeywordConfigurationRepo.findBykeywordConfigurationStatus(NotificationConfigurationStatus.ENABLED);
                    for (UserKeywordConfiguration config : keywordConfigs) {
                        String keyword = config.getKeyword().toLowerCase();
                        if ((article.getTitle() != null && article.getTitle().toLowerCase().contains(keyword)) ||
                                (article.getDescription() != null && article.getDescription().toLowerCase().contains(keyword))) {

                            Notification notification = new Notification();
                            notification.setArticle(article);
                            notification.setUser(config.getUser());
                            notification.setNotificationRead(false);
                            notification.setEmailSent(false);
                            notificationsToSave.add(notification);
                        }
                    }

                    notificationRepository.saveAll(notificationsToSave);
                }
                server.setStatus(ServerStatus.ACTIVE);
            }catch (Exception e){
                server.setStatus(ServerStatus.INACTIVE);
                System.out.println(e.getMessage());
            }
            server.setLastAccessed(LocalDateTime.now());
            externalServerRepository.save(server);
        }
    }
}
