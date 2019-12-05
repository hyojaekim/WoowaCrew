package woowacrew.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.domain.*;
import woowacrew.article.exception.MisMatchUserException;
import woowacrew.article.exception.NotFoundArticleException;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.List;

@Service
@Transactional
public class ArticleInternalService {
    private ArticleRepository articleRepository;
    private UserInternalService userInternalService;

    @Autowired
    public ArticleInternalService(ArticleRepository articleRepository, UserInternalService userInternalService) {
        this.articleRepository = articleRepository;
        this.userInternalService = userInternalService;
    }

    public Article save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        User user = userInternalService.findByOauthId(userContext.getOauthId());
        Article article = ArticleConverter.articleDtoToArticle(articleRequestDto, user);
        articleRepository.save(article);
        return article;
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAllByOrderByIdDesc();
    }

    public Article update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        Article article = findById(articleUpdateDto.getArticleId());

        article.update(user, articleUpdateDto.getTitle(), articleUpdateDto.getContent());

        return article;
    }

    public void delete(Long articleId, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        Article article = findById(articleId);

        if (!article.isAuthor(user)) {
            throw new MisMatchUserException();
        }

        articleRepository.delete(article);
    }
}
