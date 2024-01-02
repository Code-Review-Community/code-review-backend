package com.code.reviewer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.repository.ArticleRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks ArticleService articleService;
    @Mock ArticleRepository articleRepository;

    @DisplayName("게시글 정보를 입력하면 게시글을 저장한다 - 성공")
    @Test
    void saveArticle_Success() {
        //given
        Article article = Article.of("제목", "내용", "#해시태그1#해시태그2");
        given(articleRepository.save(any(Article.class))).willReturn(article);
        //when
        articleService.saveArticle(ArticleDto.from(article));
        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 제목 키워드를 입력하면 제목에 키워드가 포함된 게시글 리스트를 반환한다.")
    @Test
    void searchArticlesByTitle_Success() {
        //given
        List<Article> articles = List.of(
                Article.of("제목1", "내용1", "#해시태그"),
                Article.of("제목2", "내용2", "#해시태그")
        );
        given(articleRepository.findAllByTitleContainingIgnoreCase(anyString())).willReturn(articles);
        //when
        List<ArticleDto> articleDtos = articleService.searchArticlesByTitle("keyword");
        //then
        assertThat(articleDtos.size()).isEqualTo(articles.size());
    }
}